package com.thinkcoo.mobile.model.rest;


import com.thinkcoo.mobile.model.rest.apis.AccountApi;

/**
 * Created by Robert.yao on 2016/3/25.
 */
public interface ApiFactory {
      int cacheSize();
      void putWebNode(String webNodeKey, String webNodeUrl);
      int getWebNodeSize();
      <T> T createApiByClass(Class<T> accountApiClass);
}
