package com.liwshuo.presentation;

import android.app.Application;

import com.liwshuo.presentation.internal.di.components.ApplicationComponent;
import com.liwshuo.presentation.internal.di.components.DaggerApplicationComponent;
import com.liwshuo.presentation.internal.di.modules.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by lishuo on 16/7/27.
 */

public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;
    private static AndroidApplication application;
    public String sessionToken = "";
    public boolean hasLogin = false;
    public boolean isAutoLoginChecked = false;
    public String userId = "";

    public AndroidApplication() {
        super();
        application = this;
    }

    public static AndroidApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.initInjector();
        this.initLeakDetection();
    }

    private void initInjector() {
        this.applicationComponent =
                DaggerApplicationComponent.builder()
                        .applicationModule(new ApplicationModule(this))
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
