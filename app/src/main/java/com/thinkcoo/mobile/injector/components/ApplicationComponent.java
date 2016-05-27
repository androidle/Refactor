package com.thinkcoo.mobile.injector.components;

import android.content.Context;

import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.entity.License;
import com.thinkcoo.mobile.model.repository.AccountRepository;
import com.thinkcoo.mobile.model.repository.AppLaunchRepository;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/3/21.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    /** inject to BaseActivity */
    void inject(BaseActivity baseActivity);
    void inject(BaseSimpleActivity baseSimpleActivity);
    /** global App Context */
    Context provideApplicationContext();
    /** Threads **/
    @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler provideExecutorThread();
    @Named(ApplicationModule.UI_THREAD_NAMED) Scheduler provideUiThread();
    @Named(ApplicationModule.EXECUTOR_SINGLE_THREAD_NAMED) Scheduler provideSigleThreadExecutor();
    @Named("user") License provideUserLicense();
    /** Repositorys **/
    AccountRepository provideAccountRepository();
    AppLaunchRepository provideAppLaunchRepository();

}
