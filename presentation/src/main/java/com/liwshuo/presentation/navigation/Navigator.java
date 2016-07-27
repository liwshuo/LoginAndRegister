package com.liwshuo.presentation.navigation;

import android.content.Context;
import android.widget.Toast;

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

    public void toast(Context context) {
        Toast.makeText(context, "toast", Toast.LENGTH_LONG).show();
    }
}
