package com.liwshuo.presentation.presenter;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.liwshuo.domain.User;
import com.liwshuo.domain.interactor.DefaultSubscriber;
import com.liwshuo.domain.interactor.LoginUser;
import com.liwshuo.domain.interactor.UseCase;
import com.liwshuo.presentation.AndroidApplication;
import com.liwshuo.presentation.mapper.UserModelDataMapper;
import com.liwshuo.presentation.model.UserModel;
import com.liwshuo.presentation.view.UserLoginView;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by lishuo on 16/7/31.
 */

public class UserLoginPresenter implements Presenter {

    private UserLoginView userLoginView;

    private final UseCase loginUserUseCase;

    @Inject
    UserModelDataMapper userModelDataMapper;

    @Inject
    UserLoginPresenter(@Named("login")UseCase loginUserUseCase) {
        this.loginUserUseCase = loginUserUseCase;
    }

    public void setView(@NonNull UserLoginView view) {
        this.userLoginView = view;
    }

    public void login(String username, String password) {
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

    private final class UserLoginSubscriber extends DefaultSubscriber<User> {

        @Override
        public void onCompleted() {
            super.onCompleted();
            Logger.e("complete");
//            UserLoginPresenter.this.hideViewLoading();
        }

        @Override
        public void onNext(User user) {
            super.onNext(user);
            Logger.e("onNext");
            UserModel userModel = UserLoginPresenter.this.userModelDataMapper.transform(user);
            userLoginView.renderUser(userModel);
            AndroidApplication.getApplication().hasLogin = true;
            AndroidApplication.getApplication().userId = user.getObjectId();
//            UserDetailPresenter.this.showUserDetailInView(userModel);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if (e instanceof HttpException) {
                ResponseBody body = ((HttpException) e).response().errorBody();
            }
            Logger.e(e.getLocalizedMessage());
            AndroidApplication.getApplication().hasLogin = false;
//            UserDetailPresenter.this.hideViewLoading();
//            UserDetailPresenter.this.showErrorMessage();
//            UserDetailPresenter.this.showViewRetry();
        }
    }
}
