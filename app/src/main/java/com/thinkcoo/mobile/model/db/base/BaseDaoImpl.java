package com.thinkcoo.mobile.model.db.base;

import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public class BaseDaoImpl<T> implements BaseDao<T>{

    @Override
    public boolean insert(T... t) {
        return false;
    }

    @Override
    public boolean delete(T t, Object id) {
        return false;
    }

    @Override
    public boolean update(T... t) {
        return false;
    }

    @Override
    public boolean update(String where, T... t) {
        return false;
    }

    @Override
    public T queryById(Class<T> tClass, Object id) {
        return null;
    }

    @Override
    public List<T> queryAll(Class<T> tClass) {
        return null;
    }
}
