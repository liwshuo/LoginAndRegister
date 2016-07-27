package com.liwshuo.presentation;

import android.app.Application;

import com.liwshuo.presentation.internal.di.components.ApplicationComponent;
import com.liwshuo.presentation.internal.di.components.DaggerApplicationComponent;
import com.liwshuo.presentation.internal.di.modules.AppllicationModule;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by lishuo on 16/7/27.
 */

public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initInjector();
        this.initLeakDetection();
    }

    private void initInjector() {
        this.applicationComponent =
                DaggerApplicationComponent.builder()
                        .appllicationModule(new AppllicationModule(this))
                        .build();
    }

    private void initLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
