package com.liwshuo.presentation.presenter;

import android.support.annotation.NonNull;

import com.liwshuo.domain.User;
import com.liwshuo.domain.interactor.DefaultSubscriber;
import com.liwshuo.domain.interactor.LoginUser;
import com.liwshuo.domain.interactor.RegisterUser;
import com.liwshuo.domain.interactor.UseCase;
import com.liwshuo.presentation.AndroidApplication;
import com.liwshuo.presentation.mapper.UserModelDataMapper;
import com.liwshuo.presentation.model.UserModel;
import com.liwshuo.presentation.view.UserLoginView;
import com.liwshuo.presentation.view.UserRegisterView;
import com.orhanobut.logger.Logger;

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
    public UserRegisterPresenter(@Named("register") UseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    public void setView(@NonNull UserRegisterView view) {
        this.userRegisterView = view;
    }

    public void register(String username, String email, String password) {
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

    private final class UserRegisterSubscriber extends DefaultSubscriber<User> {

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
            UserModel userModel = UserRegisterPresenter.this.userModelDataMapper.transform(user);
            userRegisterView.renderUser(userModel);
            AndroidApplication.getApplication().hasLogin = true;
            AndroidApplication.getApplication().userId = user.getObjectId();
//            UserDetailPresenter.this.showUserDetailInView(userModel);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Logger.e(e.getMessage());
            AndroidApplication.getApplication().hasLogin = false;
//            UserDetailPresenter.this.hideViewLoading();
//            UserDetailPresenter.this.showErrorMessage();
//            UserDetailPresenter.this.showViewRetry();
        }
    }
}
