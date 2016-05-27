package com.thinkcoo.mobile.presentation.views.activitys.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thinkcoo.mobile.ThinkcooApp;
import com.thinkcoo.mobile.injector.components.ApplicationComponent;
import com.thinkcoo.mobile.presentation.views.dialog.GlobalToast;

import javax.inject.Inject;

/**
 * Created by admin on 2016/5/26.
 */
public class BaseSimpleActivity extends AppCompatActivity {

    @Inject
    public Navigator mNavigator;
    @Inject
    public GlobalToast mGlobalToast;
    @Inject
    public ActivityHistoryStack mActivityHistoryStack;

    private BaseActivityDelegate mBaseActivityDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectAppComponent();
        mBaseActivityDelegate = new BaseActivityDelegateImpl(this,mActivityHistoryStack);
        mBaseActivityDelegate.onCreate(savedInstanceState);
    }

    private void injectAppComponent() {
        getApplicationComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBaseActivityDelegate.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaseActivityDelegate.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBaseActivityDelegate.onStop();
    }
    protected ApplicationComponent getApplicationComponent(){
        return ((ThinkcooApp)this.getApplication()).getApplicationComponent();
    }

}
