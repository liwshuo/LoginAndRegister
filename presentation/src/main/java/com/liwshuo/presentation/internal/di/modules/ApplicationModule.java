package com.liwshuo.presentation.internal.di.modules;

import android.content.Context;
import android.support.annotation.UiThread;

import com.liwshuo.data.cache.UserCache;
import com.liwshuo.data.cache.UserCacheImpl;
import com.liwshuo.data.executor.JobExecutor;
import com.liwshuo.data.repository.UserDataRepository;
import com.liwshuo.domain.executor.PostExecutionThread;
import com.liwshuo.domain.executor.ThreadExecutor;
import com.liwshuo.domain.repository.UserRepository;
import com.liwshuo.presentation.AndroidApplication;
import com.liwshuo.presentation.UIThread;
import com.liwshuo.presentation.navigation.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lishuo on 16/7/27.
 */

@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    UserCache provideUserCache(UserCacheImpl userCache) {
        return userCache;
    }


    @Provides
    @Singleton
    UserRepository provideUserRepository(UserDataRepository userDataRepository) {
        return userDataRepository;
    }

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }
}
