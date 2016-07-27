package com.liwshuo.presentation.internal.di.components;

import android.content.Context;

import com.liwshuo.domain.executor.PostExecutionThread;
import com.liwshuo.domain.executor.ThreadExecutor;
import com.liwshuo.domain.repository.UserRepository;
import com.liwshuo.presentation.internal.di.PerActivity;
import com.liwshuo.presentation.internal.di.modules.AppllicationModule;
import com.liwshuo.presentation.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by lishuo on 16/7/27.
 */

@Singleton
@Component(modules = AppllicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    UserRepository userRepository();
}
