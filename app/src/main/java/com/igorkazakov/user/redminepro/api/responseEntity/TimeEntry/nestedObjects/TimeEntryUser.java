package com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects;

/**
 * Created by user on 13.07.17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeEntryUser {

    @SerializedName("id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String userName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer id) {
        this.userId = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }
}
