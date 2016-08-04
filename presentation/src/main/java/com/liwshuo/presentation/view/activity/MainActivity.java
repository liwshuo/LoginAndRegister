package com.liwshuo.presentation.view.activity;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;

import com.liwshuo.presentation.R;
import com.liwshuo.presentation.internal.di.HasComponent;
import com.liwshuo.presentation.internal.di.components.DaggerUserComponent;
import com.liwshuo.presentation.internal.di.components.UserComponent;
import com.liwshuo.presentation.internal.di.modules.UserModule;
import com.liwshuo.presentation.navigation.Navigator;
import com.liwshuo.presentation.view.fragment.UserDetailFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements HasComponent {
    private static final String INTENT_EXTRA_PARAM_USER_ID = "INTENT_PARAM_USER_ID";
    private static final String INSTANCE_STATE_PARAM_USER_ID = "STATE_PARAM_USER_ID";

    @BindView(R.id.rootLayout)
    FrameLayout rootLayout;

    private String userId;
    private UserComponent userComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initCircularReveal(savedInstanceState);
        initActivity(savedInstanceState);
        initInjector();
    }

    private void initCircularReveal(final Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            initActivity(savedInstanceState);
            return;
        }
        if (savedInstanceState == null) {
            rootLayout.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity(savedInstanceState);
                        rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void circularRevealActivity(final Bundle savedInstanceState) {

        Intent intent = getIntent();
        int cx = intent.getIntExtra("startX", rootLayout.getWidth() / 2);
        int cy = intent.getIntExtra("startY", rootLayout.getHeight() / 2);

        float finalRadius = Math.max(rootLayout.getWidth(), rootLayout.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = null;
        circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, 0, finalRadius);
        circularReveal.setDuration(500);
        circularReveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                initActivity(savedInstanceState);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        // make the view visible and start the animation
        rootLayout.setVisibility(View.VISIBLE);
        circularReveal.start();
    }

    private void initActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            userId = getIntent().getStringExtra(INTENT_EXTRA_PARAM_USER_ID);
            addFragment(R.id.fragmentContainer, new UserDetailFragment());
        }else {
            userId = savedInstanceState.getString(INSTANCE_STATE_PARAM_USER_ID);
        }
    }

    private void initInjector() {
        userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userModule(new UserModule(userId))
                .build();
    }

    @Override
    public Object getComponent() {
        return userComponent;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        moveTaskToBack(true);
    }

    public static void launchActivity(Context context, String userId) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_USER_ID, userId);
        context.startActivity(intent);
    }

    public static void launchActivity(Context context, String userId, int startX, int startY) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_USER_ID, userId);
        intent.putExtra("startX", startX);
        intent.putExtra("startY", startY);
        context.startActivity(intent);
    }
}
