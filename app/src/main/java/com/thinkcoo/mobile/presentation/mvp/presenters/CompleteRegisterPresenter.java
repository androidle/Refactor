package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.account.CompleteRegisterUseCase;
import com.thinkcoo.mobile.domain.account.LoginUseCase;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.CompleteRegisterView;
import com.thinkcoo.mobile.utils.InputCheckUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/5/24.
 */
public class CompleteRegisterPresenter extends MvpBasePresenter<MvpView> {

    CompleteRegisterUseCase mCompleteRegisterUseCase;
    LoginUseCase mLoginUseCase;
    InputCheckUtil mInputCheckUtil;
    ErrorMessageFactory mErrorMessageFactory;

    private Account mAccount;

    @Inject
    public CompleteRegisterPresenter(CompleteRegisterUseCase mCompleteRegisterUseCase, LoginUseCase mLoginUseCase, InputCheckUtil mInputCheckUtil, ErrorMessageFactory mErrorMessageFactory) {
        this.mCompleteRegisterUseCase = mCompleteRegisterUseCase;
        this.mLoginUseCase = mLoginUseCase;
        this.mInputCheckUtil = mInputCheckUtil;
        this.mErrorMessageFactory = mErrorMessageFactory;
    }

    private CompleteRegisterView getCompleteRegisterView() {
        return (CompleteRegisterView) getView();
    }


    public void completeAccountRegister(String accountName) {
        if (!isViewAttached()) {
            return;
        }

        mAccount = createAccount(accountName);

        if (!checkPasswordFormat() || !checkUserName()) {
            return;
        }

        getCompleteRegisterView().showProgressDialog(R.string.loading);
        mCompleteRegisterUseCase.execute(completeRegisterSub, mAccount, getCompleteRegisterView().getUserName());

    }

    private Account createAccount(String accountName) {
        return new Account(accountName, getCompleteRegisterView().getPassWord());
    }

    private boolean checkPasswordFormat() {

        if (null == mAccount || TextUtils.isEmpty(mAccount.getPassword())) {
            getCompleteRegisterView().showToast(R.string.password_is_empty);
            return false;
        }

        if (mInputCheckUtil.checkPassword(mAccount.getPassword())) {
            getCompleteRegisterView().showToast(R.string.password_format_error);
            return false;
        }
        return true;
    }

    private boolean checkUserName() {
        String userName = getCompleteRegisterView().getUserName();
        if (TextUtils.isEmpty(userName)) {
            getCompleteRegisterView().showToast(R.string.username_is_empty);
            return false;
        }
        return true;
    }

    private Subscriber completeRegisterSub = new Subscriber<Void>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if (!isViewAttached()) {
                return;
            }
            getCompleteRegisterView().hideProgressDialogIfShowing();
            int errorMsg = mErrorMessageFactory.createStringResId(e);
            getCompleteRegisterView().showToast(errorMsg);
        }

        @Override
        public void onNext(Void v) {
            if (!isViewAttached()) {
                return;
            }
            //  start login action
            mLoginUseCase.execute(loginSub, mAccount);
        }
    };

    private Subscriber loginSub = new Subscriber<User>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if (!isViewAttached()) {
                return;
            }
            getCompleteRegisterView().hideProgressDialogIfShowing();
            getCompleteRegisterView().gotoLoginPage();
        }

        @Override
        public void onNext(User o) {
            if (!isViewAttached()) {
                return;
            }
            //// TODO: 2016/5/24 init environment --> gotoHomePage()
        }
    };
}
