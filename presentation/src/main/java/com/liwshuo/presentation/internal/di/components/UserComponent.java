package com.liwshuo.presentation.internal.di.components;

import com.liwshuo.presentation.internal.di.PerActivity;
import com.liwshuo.presentation.internal.di.modules.ActivityModule;
import com.liwshuo.presentation.internal.di.modules.UserModule;
import com.liwshuo.presentation.view.fragment.LoginFragment;
import com.liwshuo.presentation.view.fragment.UserDetailFragment;

import dagger.Component;

/**
 * Created by lishuo on 16/7/28.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {
    void inject(UserDetailFragment userDetailFragment);

    void inject(LoginFragment loginFragment);
}
