package com.liwshuo.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.liwshuo.data.repository.UserDataRepository;
import com.liwshuo.domain.User;
import com.liwshuo.domain.interactor.DefaultSubscriber;
import com.liwshuo.domain.interactor.UseCase;
import com.liwshuo.presentation.internal.di.PerActivity;
import com.liwshuo.presentation.mapper.UserModelDataMapper;
import com.liwshuo.presentation.model.UserModel;
import com.liwshuo.presentation.view.UserDetailView;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by lishuo on 16/7/28.
 */

@PerActivity
public class UserDetailPresenter implements Presenter {

    private UserDetailView userDetailView;

    private final UseCase getUserDetailUseCase;

    @Inject
    UserModelDataMapper userModelDataMapper;

    private UserModel userModel;

    //TODO:DataMap
    @Inject
    public UserDetailPresenter(@Named("userDetail") UseCase getUserDetailUseCase) {
        this.getUserDetailUseCase = getUserDetailUseCase;
    }

    public void setView(@NonNull UserDetailView view) {
        this.userDetailView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getUserDetailUseCase.unsubscribe();
        this.userDetailView = null;
    }

    public void init() {
        this.loadUserDetail();
    }

    private void loadUserDetail() {
        this.getUserDetail();
    }

    private void showViewLoading() {
        this.userDetailView.showLoading();
    }

    private void hideViewLoading() {
        this.userDetailView.hideLoading();
    }

    private void showViewRetry() {
        this.userDetailView.showRetry();
    }

    private void hideViewRetry() {
        this.userDetailView.hideRetry();
    }

    private void showErrorMessage() {
        this.userDetailView.showError();
    }

    private void showUserDetailInView(UserModel userModel) {
        this.userModel = userModel;
        this.userDetailView.renderUser(userModel);
    }

    private void getUserDetail() {
        Logger.e("getDetail");
        this.getUserDetailUseCase.execute(new UserDetailSubscriber());
    }


    private final class UserDetailSubscriber extends DefaultSubscriber<User> {

        @Override
        public void onCompleted() {
            super.onCompleted();
            Logger.e("complete");
            UserDetailPresenter.this.hideViewLoading();
        }

        @Override
        public void onNext(User user) {
            super.onNext(user);
            Logger.e("onNext");
            UserModel userModel = UserDetailPresenter.this.userModelDataMapper.transform(user);
            UserDetailPresenter.this.showUserDetailInView(userModel);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Logger.e(e.getMessage());
            UserDetailPresenter.this.hideViewLoading();
            UserDetailPresenter.this.showErrorMessage();
            UserDetailPresenter.this.showViewRetry();
        }
    }

}
