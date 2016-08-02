/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liwshuo.data.repository;


import com.liwshuo.data.entity.mapper.UserEntityDataMapper;
import com.liwshuo.data.repository.datasource.UserDataStore;
import com.liwshuo.data.repository.datasource.UserDataStoreFactory;
import com.liwshuo.domain.User;
import com.liwshuo.domain.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * {@link UserRepository} for retrieving user data.
 */
@Singleton
public class UserDataRepository implements UserRepository {

  private final UserDataStoreFactory userDataStoreFactory;
  private final UserEntityDataMapper userEntityDataMapper;

  /**
   * Constructs a {@link UserRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param userEntityDataMapper {@link UserEntityDataMapper}.
   */
  @Inject
  public UserDataRepository(UserDataStoreFactory dataStoreFactory,
      UserEntityDataMapper userEntityDataMapper) {
    this.userDataStoreFactory = dataStoreFactory;
    this.userEntityDataMapper = userEntityDataMapper;
  }

  @SuppressWarnings("Convert2MethodRef")
  @Override
  public Observable<List<User>> users() {
    //we always get all users from the cloud
    final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
    return userDataStore.userEntityList()
        .map(userEntities -> this.userEntityDataMapper.transform(userEntities));
  }

  @SuppressWarnings("Convert2MethodRef")
  @Override
  public Observable<User> user(String userId) {
    final UserDataStore userDataStore = this.userDataStoreFactory.create(userId);
    return userDataStore.userEntityDetails(userId)
        .map(userEntity -> this.userEntityDataMapper.transform(userEntity));
  }

  @Override
  public Observable<User> login(String username, String password) {
    final UserDataStore userDataStore = this.userDataStoreFactory.create();
    return userDataStore.login(username, password)
            .map(userEntity -> this.userEntityDataMapper.transform(userEntity));
  }

  public Observable<User> register(String username, String email, String password) {
    final UserDataStore userDataStore = this.userDataStoreFactory.create();
    return userDataStore.register(username, email, password)
            .map(userEntity -> this.userEntityDataMapper.transform(userEntity));
  }
}
