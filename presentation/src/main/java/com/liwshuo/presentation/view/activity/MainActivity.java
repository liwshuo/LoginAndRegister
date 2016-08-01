package com.liwshuo.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    private String userId;
    private UserComponent userComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initActivity(savedInstanceState);
        initInjector();
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
                .userModule(new UserModule("579418e5c4c97100540d0b3e"))
                .build();
    }

    @Override
    public Object getComponent() {
        return userComponent;
    }

    public static void launchActivity(Context context, String userId) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_USER_ID, userId);
        context.startActivity(intent);
    }
}
