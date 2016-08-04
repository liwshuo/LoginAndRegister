package com.liwshuo.presentation.view.activity;

import android.annotation.TargetApi;
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
import android.transition.Slide;
import android.transition.Transition;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.liwshuo.presentation.R;
import com.liwshuo.presentation.internal.di.HasComponent;
import com.liwshuo.presentation.internal.di.components.DaggerUserComponent;
import com.liwshuo.presentation.internal.di.modules.UserModule;
import com.liwshuo.presentation.model.UserModel;
import com.liwshuo.presentation.model.exception.LoginErrorResponse;
import com.liwshuo.presentation.presenter.UserLoginPresenter;
import com.liwshuo.presentation.view.UserLoginView;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

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
    @BindView(R.id.linkToRegisterScreenButton)
    Button linkToRegisterScreenButton;
    @BindView(R.id.progressBar)
    CircleProgressBar progressBar;

    @Inject
    UserLoginPresenter userLoginPresenter;

    private int startX;
    private int startY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        initTransition();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initInjector();
        userLoginPresenter.setView(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initTransition() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
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
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent, bundle);
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
        userLoginPresenter.tryLogin(usernameInput.getText().toString(), passwordInput.getText().toString());
    }

    @OnTouch(R.id.submit)
    boolean  onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = (int) event.getRawX();
            startY = (int) event.getRawY();
        }
        return super.onTouchEvent(event);
    }


    @OnClick(R.id.linkToRegisterScreenButton)
    void linkToRegisterScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Pair<View, String> p1 = Pair.create((View)usernameLayout, "usernameLayout");
            Pair<View, String> p2 = Pair.create((View)passwordLayout, "passwordLayout");
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, p1, p2);
            navigator.launchRegisterActivity(this, options.toBundle());
        }else {
            navigator.launchRegisterActivity(this);
        }
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

    @Override
    public void login(UserModel userModel) {
        if (userModel != null) {
            navigator.launchUserDetailPage(this, userModel.getObjectId(), startX, startY);
//            finish();
        }
    }

    @Override
    public void showUsernameError(LoginErrorResponse errorResponse) {
        usernameLayout.setError(errorResponse.getError());
    }

    @Override
    public void showPasswordError(LoginErrorResponse errorResponse) {
        passwordLayout.setError(errorResponse.getError());
    }

    @Override
    protected boolean isLoginActivity() {
        return true;
    }

    @Override
    public void showLoading() {
        usernameInput.setEnabled(false);
        passwordInput.setEnabled(false);
        submitButton.setEnabled(false);
        linkToRegisterScreenButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        usernameInput.setEnabled(true);
        passwordInput.setEnabled(true);
        submitButton.setEnabled(true);
        linkToRegisterScreenButton.setEnabled(true);
        progressBar.setVisibility(View.GONE);
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
