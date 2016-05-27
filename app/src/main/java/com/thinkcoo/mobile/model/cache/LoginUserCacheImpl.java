package com.thinkcoo.mobile.model.cache;

import com.thinkcoo.mobile.model.entity.User;

import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by Robert.yao on 2016/3/24.
 */
@Singleton
public class LoginUserCacheImpl implements LoginUserCache {

    User user;

    @Override
    public Observable<User> get() {
        return null;
    }
    @Override
    public boolean put(User user) {
        return false;
    }
    @Override
    public boolean destroy() {
        return false;
    }
}
