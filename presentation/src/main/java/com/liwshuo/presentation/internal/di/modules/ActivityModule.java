package com.liwshuo.presentation.internal.di.modules;

import android.app.Activity;

import com.liwshuo.presentation.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lishuo on 16/7/27.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }
}
