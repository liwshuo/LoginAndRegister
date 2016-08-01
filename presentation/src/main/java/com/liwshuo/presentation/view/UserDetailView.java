package com.liwshuo.presentation.view;

import com.liwshuo.presentation.model.UserModel;

/**
 * Created by lishuo on 16/7/28.
 */

public interface UserDetailView extends LoadDataView {
    void renderUser(UserModel userModel);

    void showUserName(String userName);
}
