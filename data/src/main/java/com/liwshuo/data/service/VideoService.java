package com.liwshuo.data.service;

import com.liwshuo.data.entity.VideoEntity;
import com.liwshuo.data.entity.VideoListEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by lishuo on 16/7/24.
 */

public interface VideoService {
    @Headers({
            "X-LC-Id: T85Mq6nI9y9xqJhScED3PMf8-gzGzoHsz",
            "X-LC-Key: ikdYrUIroNwl3fEm1bMdt1UX",
            "Content-Type: application/json"
    })
    @GET("classes/Video")
    Observable<VideoListEntity> getVideos();

    @Headers({
            "X-LC-Id: T85Mq6nI9y9xqJhScED3PMf8-gzGzoHsz",
            "X-LC-Key: ikdYrUIroNwl3fEm1bMdt1UX",
            "Content-Type: application/json"
    })
    @GET("classes/Video/{objectId}")
    Observable<VideoEntity> getVideo(@Path("objectId") String objectId);
}
