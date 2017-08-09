package com.igorkazakov.user.redminepro.api.response;

import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Tracker;

import java.util.List;

/**
 * Created by user on 09.08.17.
 */

public class TrackersResponse {

    @SerializedName("trackers")
    private List<Tracker> trackers;

    public List<Tracker> getTrackers() {
        return trackers;
    }

    public void setTrackers(List<Tracker> trackers) {
        this.trackers = trackers;
    }
}
