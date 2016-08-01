package com.liwshuo.domain.interactor;

import com.liwshuo.domain.executor.PostExecutionThread;
import com.liwshuo.domain.executor.ThreadExecutor;
import com.liwshuo.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by lishuo on 16/7/28.
 */

public class GetUserDetail extends UseCase {

    private final String userId;
    private final UserRepository userRepository;

    @Inject
    public GetUserDetail(String userId, UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userId = userId;
        this.userRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.userRepository.user(this.userId);
    }
}
