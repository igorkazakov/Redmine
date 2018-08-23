package com.igorkazakov.user.redminepro.api.response;

import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.database.room.entity.TrackerEntity;

import java.util.List;

/**
 * Created by user on 09.08.17.
 */

public class TrackersResponse {

    @SerializedName("trackers")
    private List<TrackerEntity> trackers;

    public List<TrackerEntity> getTrackers() {
        return trackers;
    }

    public void setTrackers(List<TrackerEntity> trackers) {
        this.trackers = trackers;
    }
}
