package com.thinkcoo.mobile.model.db.base;

import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface BaseDao<T> {

    boolean insert(T ...t);
    boolean delete(T t , Object id);
    boolean update(T ...t);
    boolean update(String where ,T...t);
    T queryById(Class<T> tClass ,Object id);
    List<T> queryAll(Class<T> tClass);

}
