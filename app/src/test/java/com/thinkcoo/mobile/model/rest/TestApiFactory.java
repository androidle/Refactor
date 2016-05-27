//package com.thinkcoo.mobile.model.rest;
//
//import com.thinkcoo.mobile.model.rest.apis.UserApi;
//import junit.framework.Assert;
//
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * Created by Robert.yao on 2016/3/25.
// */
//public class TestApiFactory {
//
//    ApiFactory apiFactory;
//
//    @Before
//    public void setup(){
//        apiFactory = new ApiFactoryImpl();
//    }
//
//    @Test
//    public void testCreateLoginApiByKey(){
//
//        String key = "yingzi-login";
//        UserApi apibase = apiFactory.createApiByWebNodeKey(key,UserApi.class);
//        Assert.assertNotNull(apibase);
//        Assert.assertEquals(apiFactory.cacheSize(),1);
//        UserApi apibase2 = apiFactory.createApiByWebNodeKey(key,UserApi.class);
//        Assert.assertSame(apibase,apibase2);
//    }
//
//    @Test
//    public void testPutWebNode(){
//        apiFactory.putWebNode("","http://192.168.0.1");
//        Assert.assertEquals(apiFactory.getWebNodeSize(), 2);
//        apiFactory.putWebNode("","http://192.168.0.1");
//        Assert.assertEquals(apiFactory.getWebNodeSize(), 2);
//        apiFactory.putWebNode("111","http://192.168.0.1");
//        Assert.assertEquals(apiFactory.getWebNodeSize(), 3);
//    }
//
//    @Test
//    public void testPutWebNodeAndUseKeyGetApi(){
//
//        apiFactory.putWebNode("yingzi-chat-mobile","http://192.168.0.1");
//        Assert.assertEquals(apiFactory.getWebNodeSize(), 2);
//        UserApi userApi = apiFactory.createApiByWebNodeKey("yingzi-chat-mobile",UserApi.class);
//        Assert.assertNotNull(userApi);
//        Assert.assertEquals(apiFactory.cacheSize(), 1);
//
//    }
//
//}
