package com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 25.07.17.
 */

public class FixedVersion {

    @SerializedName("id")
    @Expose
    private Long fixedVersionId;
    @SerializedName("name")
    @Expose
    private String fixedVersionName;
//    @SerializedName("project")
//    @Expose
//    @Embedded
//    private ShortProject project;

//    public ShortProject getProject() {
//        return project;
//    }
//
//    public void setProject(ShortProject project) {
//        this.project = project;
//    }

    public Long getFixedVersionId() {
        return fixedVersionId;
    }

    public void setFixedVersionId(Long id) {
        this.fixedVersionId = id;
    }

    public String getFixedVersionName() {
        return fixedVersionName != null ? fixedVersionName : "";
    }

    public void setFixedVersionName(String name) {
        this.fixedVersionName = name;
    }
}
