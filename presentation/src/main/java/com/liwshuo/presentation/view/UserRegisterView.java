package com.liwshuo.presentation.view;

import com.liwshuo.presentation.model.UserModel;

/**
 * Created by lishuo on 16/8/2.
 */

public interface UserRegisterView extends LoadDataView {
    void renderUser(UserModel userModel);
}