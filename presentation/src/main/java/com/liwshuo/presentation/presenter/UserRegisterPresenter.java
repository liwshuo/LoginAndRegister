package com.liwshuo.presentation.presenter;

import android.support.annotation.NonNull;

import com.liwshuo.data.exception.RetrofitException;
import com.liwshuo.domain.User;
import com.liwshuo.domain.interactor.DefaultSubscriber;
import com.liwshuo.domain.interactor.LoginUser;
import com.liwshuo.domain.interactor.RegisterUser;
import com.liwshuo.domain.interactor.UseCase;
import com.liwshuo.presentation.AndroidApplication;
import com.liwshuo.presentation.mapper.UserModelDataMapper;
import com.liwshuo.presentation.model.UserModel;
import com.liwshuo.presentation.model.exception.LoginErrorResponse;
import com.liwshuo.presentation.view.UserLoginView;
import com.liwshuo.presentation.view.UserRegisterView;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by lishuo on 16/8/2.
 */

public class UserRegisterPresenter implements Presenter {

    private UserRegisterView userRegisterView;

    private final UseCase registerUserUseCase;

    @Inject
    UserModelDataMapper userModelDataMapper;

    @Inject
    public UserRegisterPresenter(@Named("tryRegister") UseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    public void setView(@NonNull UserRegisterView view) {
        this.userRegisterView = view;
    }

    public void tryRegister(String username, String email, String password) {
        showViewLoading();
        ((RegisterUser) registerUserUseCase).setUserInfo(username, email, password);
        registerUserUseCase.execute(new UserRegisterPresenter.UserRegisterSubscriber());
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

    private void doRegister(UserModel userModel) {
        userRegisterView.doRegister(userModel);
        AndroidApplication.getApplication().hasLogin = true;
        AndroidApplication.getApplication().userId = userModel.getObjectId();
    }

    private void hideViewLoading() {
        userRegisterView.hideLoading();
    }

    private void showViewLoading() {
        userRegisterView.showLoading();
    }

    private void showErrorMsg(LoginErrorResponse errorResponse) {
        int errorCode = errorResponse.getCode();
        Logger.e(errorResponse.getError() + errorResponse.getCode());
        switch (errorCode) {
            case 200:
            case 202:
            case 211:
            case 217:
                userRegisterView.showUsernameError(errorResponse);
                break;
            case 125:
            case 203:
            case 204:
                userRegisterView.showEmailError(errorResponse);
                break;
            case 1:
            case 201:
            case 210:
            case 218:
                userRegisterView.showPasswordError(errorResponse);
                break;
        }
    }

    private final class UserRegisterSubscriber extends DefaultSubscriber<User> {

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
            UserModel userModel = UserRegisterPresenter.this.userModelDataMapper.transform(user);
            doRegister(userModel);
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
