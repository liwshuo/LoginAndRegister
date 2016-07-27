package com.liwshuo.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishuo on 16/7/24.
 */

public class VideoList {
    private List<Video> videoEntities = new ArrayList<Video>();

    /**
     *
     * @return
     * The results
     */
    public List<Video> getVideoEntities() {
        return videoEntities;
    }

    /**
     *
     * @param videoEntities
     * The results
     */
    public void setVideoEntities(List<Video> videoEntities) {
        this.videoEntities = videoEntities;
    }

}
