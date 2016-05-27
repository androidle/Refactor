package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerAccountComponent;
import com.thinkcoo.mobile.injector.modules.AccountModule;
import com.thinkcoo.mobile.presentation.mvp.presenters.CompleteRegisterPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.CompleteRegisterView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompleteRegisterActivity extends BaseActivity implements CompleteRegisterView {

    public static final String EXTRA_ACCOUNT_NAME_KEY = "extra_account_name_key";

    public static Intent getCompleteRegisterIntent(Context context, String accountName) {
        if (TextUtils.isEmpty(accountName)) {
            throw new IllegalArgumentException("accountName is NULL !");
        }

        Intent intent = new Intent(context, CompleteRegisterActivity.class);
        intent.putExtra(EXTRA_ACCOUNT_NAME_KEY, accountName);
        return intent;
    }

    @Bind(R.id.tv_title)
    TextView viewTitle;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.change_password_visible)
    ImageView changePasswordVisible;
    @Bind(R.id.et_accountname)
    EditText etAccountName;

    @Inject
    CompleteRegisterPresenter mCompleteRegisterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_register);
        initData();
        ButterKnife.bind(this);
    }

    private void initData() {
        viewTitle.setText(getString(R.string.register));
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mCompleteRegisterPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerAccountComponent.builder().applicationComponent(getApplicationComponent()).accountModule(new AccountModule()).build().inject(this);
    }

    @Override
    public String getPassWord() {
        return etPassword.getText().toString().trim();
    }

    @Override
    public String getUserName() {
        return etAccountName.getText().toString().trim();
    }

    @Override
    public void gotoHomePage() {
        mNavigator.navigateToHome(this);
    }

    @Override
    public void gotoLoginPage() {
        mNavigator.navigateToLogin(this);
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

    @OnClick({R.id.iv_back, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                closeSelf();
                break;
            case R.id.iv_change_password_visible:
                changePasswordVisible();
                break;
            case R.id.btn_commit:
                mCompleteRegisterPresenter.completeAccountRegister(getIntent().getStringExtra(EXTRA_ACCOUNT_NAME_KEY));
                break;
        }
    }

    private void changePasswordVisible() {
        if (etPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            showPassword();
        } else {
            hidePassword();
        }
    }

    private void hidePassword() {
        changePasswordVisible.setImageDrawable(getResources().getDrawable(
                R.mipmap.ic_password_normal));
        etPassword
                .setTransformationMethod(PasswordTransformationMethod
                        .getInstance());
    }

    private void showPassword() {
        changePasswordVisible.setImageDrawable(getResources().getDrawable(
                R.mipmap.ic_password_checked));
        etPassword
                .setTransformationMethod(HideReturnsTransformationMethod
                        .getInstance());
    }

}
