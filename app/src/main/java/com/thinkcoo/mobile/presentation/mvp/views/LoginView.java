package com.thinkcoo.mobile.presentation.mvp.views;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Robert.yao on 2016/3/22.
 */
public interface LoginView extends MvpView , BaseHintView , BaseActivityHelpView{

    String getPassword();
    String getPhoneNumber();
    void setPassword(String password);
    void setPhoneNumber(String phoneNumber);
    void gotoHomePage();
}
