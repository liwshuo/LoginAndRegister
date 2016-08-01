package com.liwshuo.presentation.view;

import com.liwshuo.presentation.model.UserModel;

/**
 * Created by lishuo on 16/7/31.
 */

public interface UserLoginView extends LoadDataView {
    void renderUser(UserModel userModel);

}
