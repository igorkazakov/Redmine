package com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 25.07.17.
 */

public class AssignedTo {

    @SerializedName("id")
    @Expose
    private Long assignedToId;
    @SerializedName("name")
    @Expose
    private String assignedToName;

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long id) {
        this.assignedToId = id;
    }

    public String getAssignedToName() {
        return assignedToName != null ? assignedToName : "";
    }

    public void setAssignedToName(String name) {
        this.assignedToName = name;
    }
}
