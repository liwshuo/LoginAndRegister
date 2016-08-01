package com.liwshuo.domain.interactor;

import com.liwshuo.domain.executor.PostExecutionThread;
import com.liwshuo.domain.executor.ThreadExecutor;
import com.liwshuo.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by lishuo on 16/7/31.
 */

public class LoginUser extends UseCase {
    private String username;
    private String password;
    private final UserRepository userRepository;

    @Inject
    public LoginUser(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    public void setUserInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.userRepository.login(username, password);
    }
}
