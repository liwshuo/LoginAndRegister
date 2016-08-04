package com.liwshuo.presentation.presenter;

import android.support.annotation.NonNull;

import com.liwshuo.data.exception.RetrofitException;
import com.liwshuo.domain.User;
import com.liwshuo.domain.interactor.DefaultSubscriber;
import com.liwshuo.domain.interactor.LoginUser;
import com.liwshuo.domain.interactor.UseCase;
import com.liwshuo.presentation.AndroidApplication;
import com.liwshuo.presentation.mapper.UserModelDataMapper;
import com.liwshuo.presentation.model.UserModel;
import com.liwshuo.presentation.model.exception.LoginErrorResponse;
import com.liwshuo.presentation.view.UserLoginView;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by lishuo on 16/7/31.
 */

public class UserLoginPresenter implements Presenter {

    private UserLoginView userLoginView;

    private final UseCase loginUserUseCase;

    @Inject
    UserModelDataMapper userModelDataMapper;

    @Inject
    UserLoginPresenter(@Named("tryLogin")UseCase loginUserUseCase) {
        this.loginUserUseCase = loginUserUseCase;
    }

    public void setView(@NonNull UserLoginView view) {
        this.userLoginView = view;
    }

    public void tryLogin(String username, String password) {
        showViewLoading();
        ((LoginUser) loginUserUseCase).setUserInfo(username, password);
        loginUserUseCase.execute(new UserLoginSubscriber());
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    private void doLogin(UserModel userModel) {
        userLoginView.login(userModel);
        AndroidApplication.getApplication().hasLogin = true;
        AndroidApplication.getApplication().userId = userModel.getObjectId();
    }

    private void hideViewLoading() {
        userLoginView.hideLoading();
    }

    private void showViewLoading() {
        userLoginView.showLoading();
    }

    private void showErrorMsg(LoginErrorResponse errorResponse) {
        int errorCode = errorResponse.getCode();
        Logger.e(errorResponse.getError() + errorResponse.getCode());
        switch (errorCode) {
            case 200:
            case 202:
            case 211:
            case 217:
                userLoginView.showUsernameError(errorResponse);
                break;
            case 1:
            case 201:
            case 210:
            case 218:
                userLoginView.showPasswordError(errorResponse);
                break;
        }
    }

    private final class UserLoginSubscriber extends DefaultSubscriber<User> {

        @Override
        public void onCompleted() {
            super.onCompleted();
            Logger.e("complete");
            hideViewLoading();
        }

        @Override
        public void onNext(User user) {
            super.onNext(user);
            Logger.e("onNext");
            UserModel userModel = UserLoginPresenter.this.userModelDataMapper.transform(user);
            doLogin(userModel);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            AndroidApplication.getApplication().hasLogin = false;
            RetrofitException error = (RetrofitException) e;
            try {
                LoginErrorResponse errorResponse = error.getErrorBodyAs(LoginErrorResponse.class);
                showErrorMsg(errorResponse);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
           hideViewLoading();
        }
    }
}
