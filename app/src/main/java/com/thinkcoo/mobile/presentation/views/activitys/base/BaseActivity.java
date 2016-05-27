package com.thinkcoo.mobile.presentation.views.activitys.base;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.ThinkcooApp;
import com.thinkcoo.mobile.injector.components.ApplicationComponent;
import com.thinkcoo.mobile.presentation.views.dialog.GlobalToast;

import javax.inject.Inject;


/**
 * Created by Administrator on 2016/3/16.
 */
public abstract class BaseActivity extends MvpActivity<MvpView , MvpPresenter<MvpView>>{

    @Inject
    public Navigator mNavigator;
    @Inject
    public GlobalToast mGlobalToast;
    @Inject
    public ActivityHistoryStack mActivityHistoryStack;

    protected BaseActivityDelegate mBaseActivityDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseActivityDelegate = new BaseActivityDelegateImpl(this,mActivityHistoryStack);
        mBaseActivityDelegate.onCreate(savedInstanceState);
        mActivityHistoryStack.inStack(this);
        injectAppComponent();
    }

    private void injectAppComponent() {
        getApplicationComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaseActivityDelegate.onDestroy();
        mActivityHistoryStack.outStack(this);
    }

    protected ApplicationComponent getApplicationComponent(){
        return ((ThinkcooApp)this.getApplication()).getApplicationComponent();
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        initDaggerInject();
        return generatePresenter();
    }

    protected abstract MvpPresenter generatePresenter();
    protected abstract void initDaggerInject();

}
