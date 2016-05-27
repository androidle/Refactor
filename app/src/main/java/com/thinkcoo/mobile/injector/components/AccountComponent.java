package com.thinkcoo.mobile.injector.components;

import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.AccountModule;
import com.thinkcoo.mobile.presentation.views.activitys.CompleteRegisterActivity;
import com.thinkcoo.mobile.presentation.views.activitys.LoginActivity;
import com.thinkcoo.mobile.presentation.views.activitys.RequestRegisterActivity;

import dagger.Component;

/**
 * Created by Administrator on 2016/5/20.
 */
@Component(dependencies = ApplicationComponent.class , modules = {AccountModule.class})
@ActivityScope
public interface AccountComponent {
    void inject(LoginActivity loginActivity);
    void inject(RequestRegisterActivity requestRegisterActivity);
    void inject(CompleteRegisterActivity completeRegisterActivity);
}
