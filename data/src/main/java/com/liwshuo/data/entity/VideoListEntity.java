package com.liwshuo.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishuo on 16/7/24.
 */

public class VideoListEntity {

    @SerializedName("results")
    @Expose
    private List<VideoEntity> videoEntities = new ArrayList<VideoEntity>();

    /**
     *
     * @return
     * The results
     */
    public List<VideoEntity> getVideoEntities() {
        return videoEntities;
    }

    /**
     *
     * @param videoEntities
     * The results
     */
    public void setVideoEntities(List<VideoEntity> videoEntities) {
        this.videoEntities = videoEntities;
    }

}
