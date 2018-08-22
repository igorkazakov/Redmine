package com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 25.07.17.
 */

public class Tracker {

    @SerializedName("id")
    @Expose
    private Long trackerId;
    @SerializedName("name")
    @Expose
    private String trackerName;

    public Long getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(Long id) {
        this.trackerId = id;
    }

    public String getTrackerName() {
        return trackerName != null ? trackerName : "";
    }

    public void setTrackerName(String name) {
        this.trackerName = name;
    }
}
