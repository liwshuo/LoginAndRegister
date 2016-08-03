package com.liwshuo.presentation.view.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.widget.Button;
import android.widget.EditText;

import com.liwshuo.presentation.BuildConfig;
import com.liwshuo.presentation.R;
import com.liwshuo.presentation.internal.di.HasComponent;
import com.liwshuo.presentation.internal.di.components.DaggerUserComponent;
import com.liwshuo.presentation.internal.di.components.UserComponent;
import com.liwshuo.presentation.internal.di.modules.UserModule;
import com.liwshuo.presentation.model.UserModel;
import com.liwshuo.presentation.navigation.Navigator;
import com.liwshuo.presentation.presenter.UserLoginPresenter;
import com.liwshuo.presentation.view.UserLoginView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements HasComponent, UserLoginView{

    @BindView(R.id.username)
    EditText usernameInput;
    @BindView(R.id.password)
    EditText passwordInput;
    @BindView(R.id.submit)
    Button submitButton;
    @BindView(R.id.usernameLayout)
    TextInputLayout usernameLayout;
    @BindView(R.id.passwordLayout)
    TextInputLayout passwordLayout;

    @Inject
    UserLoginPresenter userLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initTransition();
        ButterKnife.bind(this);
        initInjector();
        userLoginPresenter.setView(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initTransition() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        Transition exitTrans = new Fade();
        getWindow().setExitTransition(exitTrans);

        Transition reenterTrans = new Fade();
        getWindow().setReenterTransition(reenterTrans);

        Transition enterTrans = new Fade();
        getWindow().setEnterTransition(enterTrans);
    }

    private void initInjector() {
        DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userModule(new UserModule())
                .build().inject(this);
    }

    public static void launchActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.submit)
    void onSubmit() {
        if (TextUtils.isEmpty(usernameInput.getText())) {
            usernameLayout.setError(getResources().getString(R.string.error_username_null));
            return;
        }
        usernameLayout.setError("");
        if (TextUtils.isEmpty(passwordInput.getText())) {
            passwordLayout.setError(getResources().getString(R.string.error_password_null));
            return;
        }
        passwordLayout.setError("");
        userLoginPresenter.login(usernameInput.getText().toString(), passwordInput.getText().toString());
    }

    @OnClick(R.id.linkToRegisterScreenButton)
    void linkToRegisterScreen() {
        navigator.launchRegisterActivity(this);
    }

    @Override
    public void renderUser(UserModel userModel) {
        if (userModel != null) {
            navigator.launchUserDetailPage(this, userModel.getObjectId());
            finish();
//            navigator.toast(getActivity(), userModel.getUsername());
        }
    }

    @Override
    protected boolean isLoginActivity() {
        return true;
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
        return this;
    }

    @Override
    public Object getComponent() {
        return null;
    }
}
