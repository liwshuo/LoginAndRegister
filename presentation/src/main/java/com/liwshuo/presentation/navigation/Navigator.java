package com.liwshuo.presentation.navigation;

import android.content.Context;
import android.widget.Toast;

import com.liwshuo.presentation.view.activity.LoginActivity;
import com.liwshuo.presentation.view.activity.MainActivity;
import com.liwshuo.presentation.view.activity.RegisterActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by lishuo on 16/7/27.
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator() {

    }

    public void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public void launchUserDetailPage(Context context, String userId) {
        MainActivity.launchActivity(context, userId);
    }

    public void launchLoginActivity(Context context) {
        LoginActivity.launchActivity(context);
    }

    public void launchRegisterActivity(Context context) {
        RegisterActivity.launchActivity(context);
    }
}
