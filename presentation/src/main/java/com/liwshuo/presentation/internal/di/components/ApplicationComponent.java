package com.liwshuo.presentation.internal.di.components;

import android.content.Context;

import com.liwshuo.domain.executor.PostExecutionThread;
import com.liwshuo.domain.executor.ThreadExecutor;
import com.liwshuo.domain.repository.UserRepository;
import com.liwshuo.presentation.internal.di.modules.ApplicationModule;
import com.liwshuo.presentation.navigation.Navigator;
import com.liwshuo.presentation.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by lishuo on 16/7/27.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //暴露给sub-graphs
    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    UserRepository userRepository();

    Navigator navigator();
}
