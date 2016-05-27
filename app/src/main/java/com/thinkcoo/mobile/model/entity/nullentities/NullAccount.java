package com.thinkcoo.mobile.model.entity.nullentities;

import com.thinkcoo.mobile.model.entity.Account;

/**
 * Created by Administrator on 2016/5/19.
 */
public class NullAccount extends Account {

    public NullAccount() {
        super("", "");
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
