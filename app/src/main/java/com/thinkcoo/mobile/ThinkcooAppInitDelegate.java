package com.thinkcoo.mobile;
import com.thinkcoo.mobile.injector.components.ApplicationComponent;
import com.thinkcoo.mobile.injector.components.DaggerApplicationComponent;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.rest.ApiFactory;
import com.thinkcoo.mobile.model.rest.ApiFactoryImpl;

/**
 * Created by Robert.yao on 2016/5/16.
 */
public class ThinkcooAppInitDelegate {

    private ApplicationComponent mApplicationComponent;
    private ThinkcooApp mContext;
    private ApiFactory mApiFactory;

    public ThinkcooAppInitDelegate(ThinkcooApp context) {
        this.mContext = context;
    }

    public void init(){
        initApplicationComponent();
        initApiFactory();
        initJpush();
        initHx();
        initXlog();
        initLeakCanary();
    }

    private void initApiFactory() {
        mApiFactory = new ApiFactoryImpl();
    }

    /**
     * 初始化应用程序依赖组件
     */
    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(mContext)).build();
    }

    /**
     * 初始化内存监控
     */
    private void initLeakCanary() {
        if (BuildConfig.DEBUG) {
            //LeakCanary.install(this);
        }
    }
    /**
     * 初始化文件日志框架
     */
    private void initXlog() {

    }
    /**
     * 初始化环信
     */
    private void initHx() {

    }
    /**
     * 初始化极光推送
     */
    private void initJpush() {

    }

    public ApplicationComponent getmApplicationComponent() {
        return mApplicationComponent;
    }

    public ApiFactory getApiFactory(){
        return mApiFactory;
    }
}
