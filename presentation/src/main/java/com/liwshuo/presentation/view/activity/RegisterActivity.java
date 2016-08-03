package com.liwshuo.presentation.view.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;
import android.view.Window;
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
import butterknife.OnTextChanged;

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
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        initTransition();
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initInjector();
        userRegisterPresenter.setView(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initTransition() {
        Transition exitTrans = new Explode();
        getWindow().setExitTransition(exitTrans);

        Transition reenterTrans = new Slide();
        getWindow().setReenterTransition(reenterTrans);

        Transition enterTrans = new Explode();
        getWindow().setEnterTransition(enterTrans);
    }

    private void initInjector() {
        DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userModule(new UserModule())
                .build().inject(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void launchActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent, bundle);
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

    @OnTextChanged(R.id.username)
    void onUsernameTextChanged() {
        if (!TextUtils.isEmpty(usernameLayout.getError())) {
            usernameLayout.setError("");
        }
        if (TextUtils.isEmpty(usernameInput.getText())) {
            usernameLayout.setError(getResources().getString(R.string.error_username_null));
        }
    }

    @OnTextChanged(R.id.email)
    void onEmailTextChanged() {
        if (!TextUtils.isEmpty(emailLayout.getError())) {
            emailLayout.setError("");
        }
        if (TextUtils.isEmpty(emailInput.getText())) {
            emailLayout.setError(getResources().getString(R.string.error_email_null));
            return;
        }
    }

    @OnTextChanged(R.id.password)
    void onPasswordTextChanged() {
        if (!TextUtils.isEmpty(passwordLayout.getError())) {
            passwordLayout.setError("");
        }
        if (TextUtils.isEmpty(passwordInput.getText())) {
            passwordLayout.setError(getResources().getString(R.string.error_password_null));
            return;
        }
    }

//
//    @OnClick(R.id.linkToLoginScreenButton)
//    void linkToLoginScreen() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            Pair<View, String> p1 = Pair.create((View)usernameLayout, "usernameLayout");
//            Pair<View, String> p2 = Pair.create((View)passwordLayout, "passwordLayout");
//            ActivityOptionsCompat options = ActivityOptionsCompat.
//                    makeSceneTransitionAnimation(this, p1, p2);
//            navigator.launchLoginActivity(this, options.toBundle());
//        }else {
//            navigator.launchLoginActivity(this);
//        }
//        finish();
//    }

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
