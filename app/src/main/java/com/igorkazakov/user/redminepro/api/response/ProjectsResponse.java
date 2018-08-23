package com.igorkazakov.user.redminepro.api.response;

import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.database.room.entity.ProjectEntity;

import java.util.List;

/**
 * Created by user on 09.08.17.
 */

public class ProjectsResponse {

    @SerializedName("projects")
    private List<ProjectEntity> projects;

    public List<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectEntity> projects) {
        this.projects = projects;
    }
}
