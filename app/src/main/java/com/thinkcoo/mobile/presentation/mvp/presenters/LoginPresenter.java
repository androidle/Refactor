package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.account.LoginUseCase;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.LoginView;
import com.thinkcoo.mobile.utils.InputCheckUtil;
import javax.inject.Inject;
import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/3/22.
 */
public class LoginPresenter extends MvpBasePresenter<MvpView>{


    @Inject
    LoginUseCase userLoginUseCase;
    //@Inject
    //InitUserEnvironmentUseCase initUserEnvironmentUseCase;
    @Inject
    ErrorMessageFactory errorMessageFactory;
    @Inject
    InputCheckUtil inputCheckUtil;

    @Inject
    public LoginPresenter(LoginUseCase userLoginUseCase, /*InitUserEnvironmentUseCase initUserEnvironmentUseCase*/ ErrorMessageFactory errorMessageFactory , InputCheckUtil inputCheckUtil) {
        this.userLoginUseCase = userLoginUseCase;
        //this.initUserEnvironmentUseCase = initUserEnvironmentUseCase;
        this.errorMessageFactory = errorMessageFactory;
        this.inputCheckUtil = inputCheckUtil;
    }

    public void login(){
        if (!isViewAttached()){
            return;
        }
        Account account = createAccountFromView();
        if (checkInputPass(account)){
            getLoginView().showProgressDialog(R.string.loading);
            userLoginUseCase.execute(loginSub,account);
        }
    }

    public LoginView getLoginView(){
        return (LoginView)getView();
    }

    private Subscriber loginSub = new Subscriber<User>(){
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable throwable) {
            if (!isViewAttached()){
                return;
            }
            getLoginView().hideProgressDialogIfShowing();
            int errorRes = errorMessageFactory.createStringResId(throwable);
            getLoginView().showToast(errorRes);
        }

        @Override
        public void onNext(User user) {
            if (!isViewAttached()){
                return;
            }
            //initUserEnvironmentUseCase.execute(initUserEnvironmentSub);
        }
    };

    private Subscriber initUserEnvironmentSub = new Subscriber() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable throwable) {
            if (!isViewAttached()){
                return;
            }
            getLoginView().hideProgressDialogIfShowing();
            int errorRes = errorMessageFactory.createStringResId(throwable);
            getLoginView().showToast(errorRes);
        }

        @Override
        public void onNext(Object o) {
            if (!isViewAttached()){
                return;
            }
            getLoginView().hideProgressDialogIfShowing();
            getLoginView().gotoHomePage();
        }
    };

    private Account createAccountFromView() {
        String accountName = getLoginView().getPhoneNumber();
        String password = getLoginView().getPassword();
        return  new Account(accountName,password);
    }

    private boolean checkInputPass(Account account) {

        if (TextUtils.isEmpty(account.getAccountName())){
            getLoginView().showToast(R.string.account_must_be_not_empty);
            return false;
        }
        if (TextUtils.isEmpty(account.getPassword())){
            getLoginView().showToast(R.string.password_must_be_not_empty);
            return false;
        }
        if (inputCheckUtil.checkPhoneNumber(account.getAccountName())){
            getLoginView().showToast(R.string.account_name_format_error);
            return false;
        }
        if (inputCheckUtil.checkPassword(account.getPassword())){
            getLoginView().showToast(R.string.account_format_password_fail);
            return false;
        }
        return  true;
    }

}
