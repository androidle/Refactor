package com.thinkcoo.mobile.model.db;

import com.thinkcoo.mobile.model.db.base.BaseDaoImpl;
import com.thinkcoo.mobile.model.entity.Account;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/5/20.
 */
@Singleton
public class AccountDao extends BaseDaoImpl<Account>{
    @Inject
    public AccountDao() {
    }
}
