package com.liwshuo.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.liwshuo.presentation.AndroidApplication;
import com.liwshuo.presentation.R;
import com.liwshuo.presentation.internal.di.components.UserComponent;
import com.liwshuo.presentation.model.UserModel;
import com.liwshuo.presentation.navigation.Navigator;
import com.liwshuo.presentation.presenter.UserDetailPresenter;
import com.liwshuo.presentation.view.UserDetailView;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lishuo on 16/7/28.
 */

public class UserDetailFragment extends BaseFragment implements UserDetailView {

    @BindView(R.id.username)
    TextView usernameText;
    @BindView(R.id.email)
    TextView emailText;
    @BindView(R.id.logoutButton)
    Button logoutButton;

    @Inject
    UserDetailPresenter userDetailPresenter;
    @Inject
    Navigator navigator;

    public UserDetailFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(UserComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_user_detail, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.userDetailPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadUserDetail();
        }
    }

    private void loadUserDetail() {
        if (this.userDetailPresenter != null) {
            this.userDetailPresenter.init();
        }
    }

    @OnClick(R.id.logoutButton)
    void onClick() {
        AndroidApplication.getApplication().hasLogin = false;
        navigator.launchLoginActivity(getActivity());
    }

    @Override
    public void renderUser(UserModel userModel) {
//        this.userDetailPresenter.re
        if (userModel != null) {
            usernameText.setText(userModel.getUsername());
        }
        Logger.e("renderUser");
    }

    @Override
    public void showUserName(String userName) {
        navigator.toast(getActivity(), userName);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError() {

    }

    @Override
    public Context context() {
        return null;
    }
}
