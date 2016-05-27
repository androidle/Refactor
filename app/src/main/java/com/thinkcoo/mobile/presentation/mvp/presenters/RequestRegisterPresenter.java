package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.account.RequestRegisterUseCase;
import com.thinkcoo.mobile.domain.account.RequestVcodeUseCase;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.entity.License;
import com.thinkcoo.mobile.model.entity.Vcode;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.RequestRegisterView;
import com.thinkcoo.mobile.utils.InputCheckUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by admin on 2016/5/24.
 */
public class RequestRegisterPresenter extends MvpBasePresenter<MvpView> {

    private  RequestVcodeUseCase mRequestVcodeUseCase;
    private RequestRegisterUseCase mRequestRegisterUseCase;
    private ErrorMessageFactory mErrorMessageFactory;
    private InputCheckUtil mInputCheckUtil;
    private Vcode mResponseVcode;

    @Inject
    public RequestRegisterPresenter(RequestVcodeUseCase mRequestVcodeUseCase, RequestRegisterUseCase mRequestRegisterUseCase, ErrorMessageFactory mErrorMessageFactory, InputCheckUtil mInputCheckUtil) {
        this.mRequestVcodeUseCase = mRequestVcodeUseCase;
        this.mRequestRegisterUseCase = mRequestRegisterUseCase;
        this.mErrorMessageFactory = mErrorMessageFactory;
        this.mInputCheckUtil = mInputCheckUtil;
    }

    public RequestRegisterView getRequestRegisterView() {
        return (RequestRegisterView) getView();
    }

    public void requestObtainVcode() {
        if (!isViewAttached()) {
            return;
        }
        if (!checkAccount()) {
            return;
        }
        getRequestRegisterView().startVcodeCountDown();
        mRequestVcodeUseCase.execute(requestVcodeUseCaseSub, getAccountFromView());
    }

    public void requestRegister(License license) {
        if (!isViewAttached()) {
            return;
        }
        if (!checkAccount() || !checkVcode()) {
            return;
        }
        getRequestRegisterView().showProgressDialog(R.string.loading);
        mRequestRegisterUseCase.execute(requestRegisterUseCaseSub, getAccountFromView(), mResponseVcode, license);
    }

    private Subscriber requestVcodeUseCaseSub = new Subscriber<Vcode>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if (!isViewAttached()) {
                return;
            }
            getRequestRegisterView().showToast(mErrorMessageFactory.createStringResId(e));
            getRequestRegisterView().stopVcodeCountDown();
        }

        @Override
        public void onNext(Vcode vcode) {
            mResponseVcode = vcode;
        }
    };

    private Subscriber requestRegisterUseCaseSub = new Subscriber<Void>() {
        @Override
        public void onCompleted() {
            if (!isViewAttached()) {
                return;
            }
            getRequestRegisterView().hideProgressDialogIfShowing();
            getRequestRegisterView().gotoCompleteRegisterPage(getRequestRegisterView().getAccountName());
        }

        @Override
        public void onError(Throwable e) {
            if (!isViewAttached()) {
                return;
            }
            getRequestRegisterView().hideProgressDialogIfShowing();
            getRequestRegisterView().showToast(mErrorMessageFactory.createStringResId(e));
        }

        @Override
        public void onNext(Void aVoid) {

        }
    };

    private boolean checkVcode() {
        String vcodeContent = getRequestRegisterView().getVcodeContent();
        if (TextUtils.isEmpty(vcodeContent)) {
            getRequestRegisterView().showToast(R.string.vcode_must_be_not_empty);
            return false;
        }

        if (!vcodeContent.equals(mResponseVcode.getContent())) {
            getRequestRegisterView().showToast(R.string.vcode_error);
            return false;
        }
        return true;
    }

    private boolean checkAccount() {
        String accountName = getRequestRegisterView().getAccountName();
        if (TextUtils.isEmpty(accountName)) {
            getRequestRegisterView().showToast(R.string.accout_name_must_be_not_empty);
            return false;
        }

        if (!mInputCheckUtil.checkPhoneNumber(accountName)) {
            getRequestRegisterView().showToast(R.string.account_name_format_error);
            return false;
        }
        return true;
    }

    public Account getAccountFromView() {
        Account account = new Account(getRequestRegisterView().getAccountName(), "");
        return account;
    }

}
