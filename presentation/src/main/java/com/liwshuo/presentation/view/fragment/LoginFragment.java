package com.liwshuo.presentation.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.liwshuo.presentation.R;
import com.liwshuo.presentation.internal.di.components.UserComponent;
import com.liwshuo.presentation.model.UserModel;
import com.liwshuo.presentation.navigation.Navigator;
import com.liwshuo.presentation.presenter.UserLoginPresenter;
import com.liwshuo.presentation.view.UserLoginView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends BaseFragment implements UserLoginView {

    @BindView(R.id.username)
    EditText usernameInput;
    @BindView(R.id.password)
    EditText passwordInput;
    @BindView(R.id.submit)
    Button submitButton;

    @Inject
    UserLoginPresenter userLoginPresenter;

    @Inject
    Navigator navigator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(UserComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.userLoginPresenter.setView(this);
    }

    @OnClick(R.id.submit)
    void onSubmit() {
        if (TextUtils.isEmpty(usernameInput.getText())) {
            return;
        }
        if (TextUtils.isEmpty(passwordInput.getText())) {
            return;
        }
        userLoginPresenter.login(usernameInput.getText().toString(), passwordInput.getText().toString());
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

    @Override
    public void renderUser(UserModel userModel) {
        if (userModel != null) {
            navigator.launchUserDetailPage(getActivity(), userModel.getObjectId());
//            navigator.toast(getActivity(), userModel.getUsername());
        }
    }
}
