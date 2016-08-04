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
package com.liwshuo.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.liwshuo.data.entity.UserEntity;
import com.liwshuo.data.entity.mapper.UserEntityJsonMapper;
import com.liwshuo.data.exception.NetworkConnectionException;
import com.liwshuo.data.exception.RxErrorHandlingCallAdapterFactory;
import com.liwshuo.data.service.UserService;
import com.liwshuo.domain.interactor.DefaultSubscriber;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {

  private final Context context;
  private final UserEntityJsonMapper userEntityJsonMapper;
  private String BASE_URL = "https://api.leancloud.cn/1.1/";
  private Retrofit retrofit;

  /**
   * Constructor of the class
   *
   * @param context {@link Context}.
   * @param userEntityJsonMapper {@link UserEntityJsonMapper}.
   */
  public RestApiImpl(Context context, UserEntityJsonMapper userEntityJsonMapper) {
    if (context == null || userEntityJsonMapper == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }
    retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
            .build();
    this.context = context.getApplicationContext();
    this.userEntityJsonMapper = userEntityJsonMapper;
  }

//  @RxLogObservable
  @Override
  public Observable<List<UserEntity>> userEntityList() {

    return Observable.create(subscriber -> {
      if (isThereInternetConnection()) {
        try {
          String responseUserEntities = getUserEntitiesFromApi();
          if (responseUserEntities != null) {
            subscriber.onNext(userEntityJsonMapper.transformUserEntityCollection(
                responseUserEntities));
            subscriber.onCompleted();
          } else {
            subscriber.onError(new NetworkConnectionException());
          }
        } catch (Exception e) {
          subscriber.onError(new NetworkConnectionException(e.getCause()));
        }
      } else {
        subscriber.onError(new NetworkConnectionException());
      }
    });
  }

  //  @RxLogObservable
  @Override
  public Observable<UserEntity> userEntityById(final String userId) {
    UserService userService = retrofit.create(UserService.class);
    return userService.getUser(userId);
  }

  @Override
  public Observable<UserEntity> login(String username, String password) {
    UserService userService = retrofit.create(UserService.class);
    return userService.login(username, password);
  }

  @Override
  public Observable<UserEntity> register(String username, String email, String password) {
    UserService userService = retrofit.create(UserService.class);
    Map<String, String> params = new HashMap<>();
    params.put("username", username);
    params.put("email", email);
    params.put("password", password);
    return userService.register(params);
  }

  private String getUserEntitiesFromApi() throws MalformedURLException {
    return ApiConnection.createGET(API_URL_GET_USER_LIST).requestSyncCall();
  }

  private String getUserDetailsFromApi(String userId) throws MalformedURLException {
    String apiUrl = API_URL_GET_USER_DETAILS + userId + ".json";
    return ApiConnection.createGET(apiUrl).requestSyncCall();
  }

  /**
   * Checks if the device has any active internet connection.
   *
   * @return true device with internet connection, otherwise false.
   */
  private boolean isThereInternetConnection() {
    boolean isConnected;

    ConnectivityManager connectivityManager =
        (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

    return isConnected;
  }
}
