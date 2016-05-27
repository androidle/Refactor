package com.thinkcoo.mobile.presentation.views.activitys;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerAccountComponent;
import com.thinkcoo.mobile.injector.modules.AccountModule;
import com.thinkcoo.mobile.presentation.mvp.presenters.LoginPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.LoginView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

    @Bind(R.id.ac_username)
    EditText acUsername;
    @Bind(R.id.ac_imageView1)
    ImageView acImageView1;
    @Bind(R.id.ac_line)
    View acLine;
    @Bind(R.id.ac_password)
    EditText acPassword;
    @Bind(R.id.ac_imageView2)
    ImageView acImageView2;
    @Bind(R.id.layout)
    RelativeLayout layout;
    @Bind(R.id.ac_butt_login)
    Button acButtLogin;
    @Bind(R.id.text_zhuce)
    TextView textZhuce;
    @Bind(R.id.text_wjmm)
    TextView textWjmm;

    @Inject
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public String getPassword() {
        return acPassword.getText().toString().trim();
    }

    @Override
    public String getPhoneNumber() {
        return acUsername.getText().toString().trim();
    }

    @Override
    public void setPassword(String password) {
        acPassword.setText(password);
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        acUsername.setText(phoneNumber);
    }

    @Override
    public void gotoHomePage() {
        mNavigator.navigateToHome(this);
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void closeSelf() {
        finish();
    }

    @Override
    public void showProgressDialog(int stringResId) {
        mBaseActivityDelegate.showProgressDialog(stringResId);
    }

    @Override
    public void hideProgressDialogIfShowing() {
        mBaseActivityDelegate.hideProgressDialogIfShowing();
    }

    @Override
    public void showToast(int stringResMsg) {
        mGlobalToast.showShortToast(stringResMsg);
    }

    @OnClick({R.id.ac_butt_login, R.id.text_zhuce, R.id.text_wjmm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ac_butt_login:
                loginPresenter.login();
                break;
            case R.id.text_zhuce:
                mNavigator.navigateToRegister(this);
                break;
            case R.id.text_wjmm:
                mNavigator.navigateToGotPassword(this);
                break;
        }
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return loginPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerAccountComponent.builder().accountModule(new AccountModule()).build().inject(this);
    }
}
