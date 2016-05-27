package com.thinkcoo.mobile.model.rest;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.thinkcoo.mobile.model.rest.apis.AccountApi;
import com.thinkcoo.mobile.utils.log.ThinkcooLog;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Robert.yao on 2016/3/25.
 */
public class ApiFactoryImpl implements ApiFactory {


    public static  final String TAG = "ApiFactoryImpl";


    @Override
    public <T> T createApiByClass(Class<T> accountApiClass) {
        String webNodeKey = getWebNodeKey(accountApiClass);
        T t = createApiByWebNodeKey(webNodeKey,accountApiClass);
        return t;
    }

    private <T> String getWebNodeKey(Class<T> accountApiClass) {
        if (accountApiClass .equals(AccountApi.class)){
            return LOGIN_PREFIX;
        }
        throw new IllegalArgumentException("bad default");
    }

    public static String LOGINPREFIX_PUBLIC = "http://117.34.109.229:8081/";//外网登录前缀
    public static String LOGINPREFIX_PRIVATE ="http://10.21.4.43:8080/";//内网登录前缀

    /**
     * 切换网络环境请修改以下变量
     */
    public static String WORK_LOGINPREFIX = LOGINPREFIX_PRIVATE;
    /**
     * 以下是各模块节点api base url 对应的key
     */
    public static String LOGIN_PREFIX = "yingzi-login";//登录 ->这个key是为了api统一而自创的

    public static String QECHART_PREFIX = "yingzi-chat-mobile";//快信节点
    public static String COURSE_PREFIX = "yingzi-schedule-mobile";//软课表节点
    public static String PERSONAL_PREFIX = "yingzi-personal-mobile";//自信节点
    public static String TRADE_PREFIX = "yingzi-trade-mobile";//自贸区节点
    public static String CALENDAR_PREFIX = "yingzi-calendar-mobile";//勤务表节点
    public static String GETJOB_PREFIX = "yingzi-hr-mobile";//找工作

    private Map<String , WeakReference<Object>> mApiCache;
    private OkHttpClient mOkHttpClient;
    private Map<String , String> mWebNodeMap;

    public ApiFactoryImpl(){
        ThinkcooLog.d(TAG,"=== 开始构造ApiFactoryImpl ===");
        initWebNodeMap();
        initApiCache();
        initOkHttpClient();
    }

    private void initWebNodeMap() {
        mWebNodeMap = new HashMap<>();
        putLoginApiWebNodeInMap();
    }

    private void putLoginApiWebNodeInMap() {
        mWebNodeMap.put(LOGIN_PREFIX, WORK_LOGINPREFIX);
    }

    private void initApiCache() {
        mApiCache = new HashMap<>();
    }

    private void initOkHttpClient() {

        mOkHttpClient = new OkHttpClient();
        HttpLoggingInterceptor logginInterceptor = new HttpLoggingInterceptor();
        logginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient.setConnectTimeout(30,TimeUnit.SECONDS);
        mOkHttpClient.interceptors().add(logginInterceptor);
        //mOkHttpClient.setSslSocketFactory();
    }

    private <T> T createApiByWebNodeKey(String key, Class<T> tClass) {
        T t = (T)getApiFromCache(key);
        if (null == t) {
            t = (T)createApi(key ,tClass);
            cacheToMap(key,t);
        }
        return t;
    }

    private void cacheToMap(String key , Object t) {
        WeakReference<Object> apiWeakReference = new WeakReference<>(t);
        mApiCache.put(key,apiWeakReference);
    }

    private Object createApi(String key , Class aClass) {
        String baseUrl = getBaseUrl(key);
        Retrofit retrofit = newRetrofitInstanceByBaseUrl(baseUrl);
        return retrofit.create(aClass);
    }

    private Retrofit newRetrofitInstanceByBaseUrl(String baseUrl) {

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getConfigOkHttpClient())
                .build();
    }

    private OkHttpClient getConfigOkHttpClient() {
        return mOkHttpClient;
    }

    private String getBaseUrl(String key) {
        return mWebNodeMap.get(key);
    }

    private Object getApiFromCache(String key) {
        WeakReference<Object> apiBaseWeakReference = mApiCache.get(key);
        if(null != apiBaseWeakReference)
            return apiBaseWeakReference.get();
        else
            return null;
    }

    @Override
    public int cacheSize(){
        return mApiCache.size();
    }

    @Override
    public void putWebNode(String webNodeKey, String webNodeUrl) {
        mWebNodeMap.put(webNodeKey,webNodeUrl);
    }

    @Override
    public int getWebNodeSize() {
        return mWebNodeMap.size();
    }

}
