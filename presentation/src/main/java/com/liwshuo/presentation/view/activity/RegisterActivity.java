package com.liwshuo.presentation.view.activity;

import android.annotation.TargetApi;
import android.app.Activity;
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

import com.liwshuo.presentation.R;
import com.liwshuo.presentation.internal.di.components.DaggerUserComponent;
import com.liwshuo.presentation.internal.di.modules.UserModule;
import com.liwshuo.presentation.model.UserModel;
import com.liwshuo.presentation.navigation.Navigator;
import com.liwshuo.presentation.presenter.UserRegisterPresenter;
import com.liwshuo.presentation.view.UserRegisterView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements UserRegisterView {

    @BindView(R.id.username)
    EditText usernameInput;
    @BindView(R.id.email)
    EditText emailInput;
    @BindView(R.id.password)
    EditText passwordInput;
    @BindView(R.id.submit)
    Button registerSubmit;
    @BindView(R.id.usernameLayout)
    TextInputLayout usernameLayout;
    @BindView(R.id.emailLayout)
    TextInputLayout emailLayout;
    @BindView(R.id.passwordLayout)
    TextInputLayout passwordLayout;

    @Inject
    UserRegisterPresenter userRegisterPresenter;

    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initTransition();
        ButterKnife.bind(this);
        initInjector();
        userRegisterPresenter.setView(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initTransition() {
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
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean isRegisterActivity() {
        return true;
    }

    @OnClick(R.id.submit)
    void register() {
        String username = usernameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        if (TextUtils.isEmpty(username)) {
            usernameLayout.setError(getResources().getString(R.string.error_username_null));
            return;
        }
        if (TextUtils.isEmpty(email)) {
            emailLayout.setError(getResources().getString(R.string.error_email_null));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError(getResources().getString(R.string.error_password_null));
            return;
        }
        userRegisterPresenter.register(username, email, password);
    }

    @OnClick(R.id.linkToLoginScreenButton)
    void linkToLoginScreen() {
        navigator.launchLoginActivity(this);
    }

    @Override
    public void renderUser(UserModel userModel) {
        navigator.launchUserDetailPage(this, userModel.getObjectId());
        finish();
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
