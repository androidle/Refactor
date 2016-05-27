package com.thinkcoo.mobile.model.entity.nullentities;

import com.thinkcoo.mobile.model.entity.User;

/**
 * Created by Robert.yao on 2016/3/21.
 */
public class NullUser extends User {

    @Override
    public boolean isEmpty() {
        return true;
    }
}
