package com.thinkcoo.mobile.model.repository.impl;

import com.thinkcoo.mobile.model.db.AccountDao;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.entity.License;
import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.model.entity.Vcode;
import com.thinkcoo.mobile.model.entity.serverresponse.BaseResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.VcodeResponse;
import com.thinkcoo.mobile.model.exception.account.LicenseNotAgreeException;
import com.thinkcoo.mobile.model.repository.AccountRepository;
import com.thinkcoo.mobile.model.rest.apis.AccountApi;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/5/19.
 */
@Singleton
public class AccountRepositoryImpl implements AccountRepository {

    AccountDao accountDao;
    AccountApi accountApi;

    @Inject
    public AccountRepositoryImpl(AccountDao accountDao, AccountApi accountApi) {
        this.accountDao = accountDao;
        this.accountApi = accountApi;
    }
    @Override
    public Observable<User> login(Account account) {
       //TODO
        return null;
    }

    @Override
    public Observable logout(Account account) {
        return null;
    }

    @Override
    public Observable register(Account account, Vcode vcode, License license) {
        if (license.isEmpty() || !license.isAgree()){
            return Observable.error(new LicenseNotAgreeException());
        }
        return accountApi.register(account.getAccountName(),vcode.getContent(),"0","0").onErrorResumeNext(new Func1<Throwable, Observable<? extends BaseResponse>>() {
            @Override
            public Observable<? extends BaseResponse> call(Throwable throwable) {
                return null;
            }
        });
    }
    @Override
    public Observable gotPassword(Account account, Vcode vcode) {
        return accountApi.forGetPassword(account.getAccountName(),vcode.getContent(),vcode.getCert(),"1");
    }

    @Override
    public Observable changePassword(Account account) {
        return null;
    }

    @Override
    public Observable changeAccountName(Account account) {
        return null;
    }

    @Override
    public Observable<Vcode> getRequestRegisterVcode(Account account) {

        return accountApi.loadVcode(account.getAccountName(),"0","0").flatMap(new Func1<BaseResponse<VcodeResponse>, Observable<Vcode>>() {
            @Override
            public Observable<Vcode> call(BaseResponse<VcodeResponse> vcodeResponseBaseResponse) {
                return Observable.just(Vcode.fromServerResponse(vcodeResponseBaseResponse.getData()));
            }
        });
    }

    @Override
    public Observable completeRegister(Account account, String areaCode, String userName) {
        return accountApi.completeRegister(account.getAccountName(), account.getPassword(), areaCode, userName);
    }

    @Override
    public Observable<Account> getLoggedAccount() {
        return Observable.create(new Observable.OnSubscribe<Account>() {
            @Override
            public void call(Subscriber<? super Account> subscriber) {
                try{
                    subscriber.onNext(Account.NULL_ACCOUNT);//TODO from db
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }
}
