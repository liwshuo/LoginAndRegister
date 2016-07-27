package com.liwshuo.presentation.internal.di.modules;

import android.content.Context;

import com.liwshuo.domain.executor.PostExecutionThread;
import com.liwshuo.domain.executor.ThreadExecutor;
import com.liwshuo.domain.repository.UserRepository;
import com.liwshuo.presentation.AndroidApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lishuo on 16/7/27.
 */

@Module
public class AppllicationModule {
    private final AndroidApplication application;

    public AppllicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor() {
        return null;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread() {
        return null;
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository() {
        return null;
    }
}
