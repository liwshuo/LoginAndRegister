package com.liwshuo.presentation.view;

import com.liwshuo.presentation.model.UserModel;
import com.liwshuo.presentation.model.exception.LoginErrorResponse;

/**
 * Created by lishuo on 16/8/2.
 */

public interface UserRegisterView extends LoadDataView {
    void doRegister(UserModel userModel);

    void showUsernameError(LoginErrorResponse errorResponse);

    void showEmailError(LoginErrorResponse errorResponse);

    void showPasswordError(LoginErrorResponse errorResponse);
}