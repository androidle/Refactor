package com.thinkcoo.mobile.model.repository;

import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.entity.License;
import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.model.entity.Vcode;

import rx.Observable;

/**
 * Created by Administrator on 2016/5/19.
 */
public interface AccountRepository {

    Observable<Account> getLoggedAccount();
    Observable<User> login(Account account);
    Observable logout(Account account);
    Observable register(Account account,Vcode vcode, License license);
    Observable gotPassword(Account account,Vcode vcode);
    Observable changePassword(Account account);
    Observable changeAccountName(Account account);
    Observable<Vcode> getRequestRegisterVcode(Account account);
    Observable completeRegister(Account account, String areaCode, String userName);
}
