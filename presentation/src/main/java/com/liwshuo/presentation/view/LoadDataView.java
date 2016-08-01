package com.liwshuo.presentation.view;

import android.content.Context;

/**
 * Created by lishuo on 16/7/28.
 */

public interface LoadDataView {
    void showLoading();

    void hideLoading();

    void showRetry();

    void hideRetry();

    void showError();

    Context context();
}
