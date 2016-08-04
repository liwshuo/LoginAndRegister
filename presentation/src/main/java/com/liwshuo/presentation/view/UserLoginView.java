package com.liwshuo.presentation.view;

import com.liwshuo.presentation.model.UserModel;
import com.liwshuo.presentation.model.exception.LoginErrorResponse;

/**
 * Created by lishuo on 16/7/31.
 */

public interface UserLoginView extends LoadDataView {
    void login(UserModel userModel);

    void showUsernameError(LoginErrorResponse errorResponse);

    void showPasswordError(LoginErrorResponse errorResponse);
}
