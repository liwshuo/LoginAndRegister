package com.liwshuo.presentation.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liwshuo.presentation.R;
import com.liwshuo.presentation.internal.di.HasComponent;
import com.liwshuo.presentation.internal.di.components.DaggerUserComponent;
import com.liwshuo.presentation.internal.di.components.UserComponent;
import com.liwshuo.presentation.internal.di.modules.UserModule;
import com.liwshuo.presentation.view.fragment.LoginFragment;
import com.liwshuo.presentation.view.fragment.UserDetailFragment;

import butterknife.ButterKnife;

public class AccessControlActivity extends BaseActivity implements HasComponent{

    private UserComponent userComponent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_control);
        ButterKnife.bind(this);
        initActivity(savedInstanceState);
        initInjector();
    }

    private void initActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new LoginFragment());
        }
    }

    private void initInjector() {
        userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userModule(new UserModule())
                .build();
    }

    @Override
    public Object getComponent() {
        return userComponent;
    }
}
