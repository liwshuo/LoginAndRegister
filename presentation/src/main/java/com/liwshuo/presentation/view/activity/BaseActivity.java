package com.liwshuo.presentation.view.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.liwshuo.presentation.AndroidApplication;
import com.liwshuo.presentation.internal.di.components.ApplicationComponent;
import com.liwshuo.presentation.internal.di.modules.ActivityModule;
import com.liwshuo.presentation.navigation.Navigator;

import javax.inject.Inject;

/**
 * Created by lishuo on 16/7/27.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AndroidApplication.getApplication().hasLogin) {
            if (isLoginActivity()) {
                navigator.launchUserDetailPage(this, AndroidApplication.getApplication().userId);
            }
        } else {
            if(!isLoginActivity() && !isRegisterActivity()) {
                navigator.launchLoginActivity(this);
            }
        }
    }

    protected boolean isLoginActivity() {
        return false;
    }

    protected boolean isRegisterActivity() {
        return false;
    }

    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

}
