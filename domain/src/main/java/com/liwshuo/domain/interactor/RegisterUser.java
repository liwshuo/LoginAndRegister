package com.liwshuo.domain.interactor;

import com.liwshuo.domain.executor.PostExecutionThread;
import com.liwshuo.domain.executor.ThreadExecutor;
import com.liwshuo.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by lishuo on 16/8/2.
 */

public class RegisterUser extends UseCase {
    private String username;
    private String email;
    private String password;
    private final UserRepository userRepository;

    @Inject
    public RegisterUser(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    public void setUserInfo(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.userRepository.register(username, email, password);
    }
}
