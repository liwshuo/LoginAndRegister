package com.liwshuo.data.service;

import com.liwshuo.data.entity.UserEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lishuo on 16/7/23.
 */

public interface UserService {
    @Headers({
            "X-LC-Id: T85Mq6nI9y9xqJhScED3PMf8-gzGzoHsz",
            "X-LC-Key: ikdYrUIroNwl3fEm1bMdt1UX",
            "Content-Type: application/json"
    })
    @POST("users")
    Observable<UserEntity> createUser(@Body Map<String, String> params);

    @Headers({
            "X-LC-Id: T85Mq6nI9y9xqJhScED3PMf8-gzGzoHsz",
            "X-LC-Key: ikdYrUIroNwl3fEm1bMdt1UX",
    })
    @GET("users/{objectId}")
    Observable<UserEntity> getUser(@Path("objectId") String objectId);

    @Headers({
            "X-LC-Id: T85Mq6nI9y9xqJhScED3PMf8-gzGzoHsz",
            "X-LC-Key: ikdYrUIroNwl3fEm1bMdt1UX",
    })
    @GET("login")
    Observable<UserEntity> login(@Query("username") String username, @Query("password") String password);

    @Headers({
            "X-LC-Id: T85Mq6nI9y9xqJhScED3PMf8-gzGzoHsz",
            "X-LC-Key: ikdYrUIroNwl3fEm1bMdt1UX",
            "Content-Type: application/json"
    })
    @POST("users")
    Observable<UserEntity> register(@Body Map<String, String> params);

}
