package com.thinkcoo.mobile.presentation.views.activitys.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thinkcoo.mobile.ThinkcooApp;
import com.thinkcoo.mobile.injector.components.ApplicationComponent;

import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/3/24.
 */
public class BaseActivityDelegateImpl implements BaseActivityDelegate{

    AppCompatActivity mActivity;
    ActivityHistoryStack mActivityHistoryStack;
    ProgressDialog mProgressDialog;

    @Inject
    public BaseActivityDelegateImpl(AppCompatActivity activity, ActivityHistoryStack activityHistoryStack){
        mActivity = activity;
        mActivityHistoryStack = activityHistoryStack;
        initSetUpProgressDialog();
    }

    public void initSetUpProgressDialog(){
        mProgressDialog = new ProgressDialog(mActivity);
    }

    @Override
    public void onCreate(Bundle bundle) {
        mActivityHistoryStack.inStack(mActivity);
    }

    @Override
    public void onDestroy() {
        mActivityHistoryStack.outStack(mActivity);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onContentChanged() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void showProgressDialog(int stringResId) {

        if (null == mProgressDialog) {
            initSetUpProgressDialog();
        }
        if (mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }

        mProgressDialog.setMessage(mActivity.getString(stringResId));
        mProgressDialog.show();

    }

    @Override
    public void hideProgressDialogIfShowing() {
        if (null != mProgressDialog){
            mProgressDialog.dismiss();
        }
    }


}
