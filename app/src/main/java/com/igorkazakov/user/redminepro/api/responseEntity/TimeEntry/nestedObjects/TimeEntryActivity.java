package com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects;

/**
 * Created by user on 13.07.17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeEntryActivity {

    @SerializedName("id")
    @Expose
    private Integer activityId;
    @SerializedName("name")
    @Expose
    private String activityName;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer id) {
        this.activityId = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String name) {
        this.activityName = name;
    }
}