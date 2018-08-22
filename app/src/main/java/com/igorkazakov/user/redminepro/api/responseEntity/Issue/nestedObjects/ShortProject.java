package com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 25.07.17.
 */

public class ShortProject {

    @SerializedName("id")
    @Expose
    private Long shortProjectId;
    @SerializedName("name")
    @Expose
    private String shortProjectName;

    public Long getShortProjectId() {
        return shortProjectId;
    }

    public void setShortProjectId(Long id) {
        this.shortProjectId = id;
    }

    public String getShortProjectName() {
        return shortProjectName;
    }

    public void setShortProjectName(String name) {
        this.shortProjectName = name;
    }
}
