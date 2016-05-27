package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.model.entity.nullentities.NullAccount;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/19.
 */
public class Account implements Nullable, Serializable {


    public static final Account NULL_ACCOUNT = new NullAccount();

    private String accountName;
    private String password;

    public Account(String accountName, String password) {
        this.accountName = accountName;
        this.password = password;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
