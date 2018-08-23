package com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects;

/**
 * Created by user on 13.07.17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeEntryProject {

    @SerializedName("id")
    @Expose
    private Integer projectId;
    @SerializedName("name")
    @Expose
    private String projectName;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer id) {
        this.projectId = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String name) {
        this.projectName = name;
    }
}
