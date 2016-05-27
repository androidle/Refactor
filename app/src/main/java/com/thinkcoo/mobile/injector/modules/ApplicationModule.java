package com.thinkcoo.mobile.injector.modules;

import android.content.Context;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.ThinkcooApp;
import com.thinkcoo.mobile.model.cache.LoginUserCache;
import com.thinkcoo.mobile.model.cache.LoginUserCacheImpl;
import com.thinkcoo.mobile.model.entity.License;
import com.thinkcoo.mobile.model.repository.AccountRepository;
import com.thinkcoo.mobile.model.repository.AppLaunchRepository;
import com.thinkcoo.mobile.model.repository.impl.AccountRepositoryImpl;
import com.thinkcoo.mobile.model.repository.impl.AppLaunchRepositoryImpl;
import com.thinkcoo.mobile.model.rest.ApiFactory;
import com.thinkcoo.mobile.model.rest.apis.AccountApi;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.views.activitys.base.ActivityHistoryStack;
import com.thinkcoo.mobile.presentation.views.activitys.base.Navigator;
import com.thinkcoo.mobile.presentation.views.dialog.GlobalToast;
import com.thinkcoo.mobile.utils.log.ThinkcooLog;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Robert.yao on 2016/3/21.
 */
@Module
public class ApplicationModule {

    public static final String TAG  = "ApplicationModule";

    public static final String UI_THREAD_NAMED = "ui_thread";
    public static final String EXECUTOR_THREAD_NAMED = "executor_thread";
    public static final String EXECUTOR_SINGLE_THREAD_NAMED = "executor_single_thread";

    private final ThinkcooApp application;

    public ApplicationModule(ThinkcooApp application) {
        this.application = application;
    }

    /**
     * 提供全局唯一的应用程序Context对象
     * @return
     */
    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ApiFactory provideApiFactory(){
        return application.getApiFactory();
    }

    /**
     * 提供全局唯一的导航器
     */
    @Provides
    @Singleton
    Navigator provideNavigator(){
        return new Navigator();
    }

    /**
     * 提供全局唯一的Toast工具对象
     */
    @Provides
    @Singleton
    GlobalToast provideGlobalToast(Context context){
        return new GlobalToast(context);
    }

    /**
     * 提供全局唯一的Activity堆栈
     * @return
     */
    @Provides
    @Singleton
    ActivityHistoryStack provideActivityHistoryStack(){
        return new ActivityHistoryStack();
    }

    /**
     * 提供全局唯一的 RxAndroid Scheduler（工作线程）
     * @return
     */
    @Provides @Named(EXECUTOR_THREAD_NAMED) @Singleton
    Scheduler provideExecutorThread() {
        return Schedulers.io();
    }

    /**
     * 提供全局唯一的单线程队列式的线程框架
     */
    @Provides @Named(EXECUTOR_SINGLE_THREAD_NAMED) @Singleton
    Scheduler provideSignleThreadExecutor(){
        //TODO
        return null;
    }

    /**
     * 提供全局唯一的 RxAndroid Scheduler（UI线程）
     * @return
     */
    @Provides  @Named(UI_THREAD_NAMED) @Singleton
    Scheduler provideUiThread() {
        return AndroidSchedulers.mainThread();
    }

    /**
     * 提供全局唯一的用户缓存实例
     * @return
     */
    @Provides @Singleton
    LoginUserCache provideLoginUserCache(){
        return new LoginUserCacheImpl();
    }

    /**
     * 提供全局唯一的错误消息工厂
     * @return
     */
    @Provides @Singleton
    ErrorMessageFactory provideErrorMessageFactory(){
        return new ErrorMessageFactory(application.getApplicationContext());
    }

    @Provides @Singleton @Named("user")
    License provideUserLicense(){
        String userLicneseUrl = application.getResources().getString(R.string.html_protocol);
        return  new License(userLicneseUrl,"");
    }

    /********************************** Repository and Api **************************************************8*/

    @Singleton
    @Provides
    AppLaunchRepository provideAppLaunchRepository(AppLaunchRepositoryImpl appLaunchRepository){
        return  appLaunchRepository;
    }

    @Singleton
    @Provides
    AccountApi provideAccountApi(){
        ThinkcooLog.d(TAG,"=== 开始提供AccountApi ===");
        return application.getApiFactory().createApiByClass(AccountApi.class);
    }
    @Singleton
    @Provides
    AccountRepository provideAccountRepository(AccountRepositoryImpl accountRepository){
        ThinkcooLog.d(TAG,"=== 开始提供AccountRepository ===");
        return accountRepository;
    }


}
