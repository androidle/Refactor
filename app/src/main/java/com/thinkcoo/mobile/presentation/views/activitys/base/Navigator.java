package com.thinkcoo.mobile.presentation.views.activitys.base;

import android.content.Context;
import android.content.Intent;

import com.thinkcoo.mobile.model.entity.License;
import com.thinkcoo.mobile.presentation.views.activitys.CompleteRegisterActivity;
import com.thinkcoo.mobile.presentation.views.activitys.RequestRegisterActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserLicenseActivity;

import javax.inject.Inject;


/**
 * Created by Robert.yao on 2016/3/21.
 * 全局导航类，负责整个应用程序的页面跳转
 */
public class Navigator {


    @Inject
    public Navigator(){}

    /**
     * 跳转到新用户使用提醒界面
     * @param context activity context
     */
    public void navigateToNewGuidance(Context context){
        //TODO
        //context.startActivity(NewUserGuidanceActivity.getNewUserGuidanceIntent(context));
    }

    /**
     * 跳转到登录界面
     * @param context activity context
     */
    public void navigateToLogin(Context context){
        //TODO
        //context.startActivity(LoginActivity.getLoginIntent(context));
    }


    /**
     * 跳转到注册
     */
    public void navigateToRegister(Context context) {
        Intent intent = RequestRegisterActivity.getRequestRegisterIntent(context);
        context.startActivity(intent);
    }

    /**
     * 跳转到找回密码
     */
    public void navigateToGotPassword(Context context) {
        //// TODO: 2016/3/22
    }

    /**
     * 跳转到主界面
     * @param context
     */
    public void navigateToHome(Context context){
        //TODO
    }

    /***
     * 跳转到完成注册界面(设置密码)
     * @param context
     * @param phoneNumber
     */
    public void navigateToCompleteRegister(Context context , String phoneNumber) {
        Intent intent = CompleteRegisterActivity.getCompleteRegisterIntent(context, phoneNumber);
        context.startActivity(intent);
    }

    /**
     * 跳转到用户许可协议界面
     * @param context
     * @param License 用户许可协议界面依赖的实体对象
     */
    public void navigateToUserLicense(Context context, License License) {
        Intent intent = UserLicenseActivity.getUserLicenseIntent(context, License);
        context.startActivity(intent);
    }
}
