package com.liwshuo.presentation.internal.di.modules;

import com.liwshuo.domain.executor.PostExecutionThread;
import com.liwshuo.domain.executor.ThreadExecutor;
import com.liwshuo.domain.interactor.GetUserDetail;
import com.liwshuo.domain.interactor.GetUserList;
import com.liwshuo.domain.interactor.LoginUser;
import com.liwshuo.domain.interactor.RegisterUser;
import com.liwshuo.domain.interactor.UseCase;
import com.liwshuo.domain.repository.UserRepository;
import com.liwshuo.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lishuo on 16/7/28.
 */

@Module
public class UserModule {

    private String userId = "";
    private String username = "";
    private String password = "";


    public UserModule() {

    }

    public UserModule(String userId) {
        this.userId = userId;
    }

    public UserModule(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Provides
    @PerActivity
    @Named("userDetail")
    UseCase provideGetUserDetailUseCase(UserRepository userRepository, ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread) {
        return new GetUserDetail(userId, userRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("userList")
    UseCase provideGetUserListUseCase(GetUserList getUserList) {
        return getUserList;
    }

    @Provides
    @PerActivity
    @Named("login")
    UseCase provideLoginUseCase(UserRepository userRepository, ThreadExecutor threadExecutor,
                                PostExecutionThread postExecutionThread) {
        return new LoginUser(userRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("register")
    UseCase provideRegisterUseCard(UserRepository userRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread) {
        return new RegisterUser(userRepository, threadExecutor, postExecutionThread);

    }
}
