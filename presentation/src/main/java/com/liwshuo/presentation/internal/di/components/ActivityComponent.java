package com.liwshuo.presentation.internal.di.components;

import android.app.Activity;

import com.liwshuo.presentation.internal.di.PerActivity;
import com.liwshuo.presentation.internal.di.modules.ActivityModule;

import dagger.Component;

/**
 * Created by lishuo on 16/7/27.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity activity();
}
