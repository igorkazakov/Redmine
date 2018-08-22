package com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 25.07.17.
 */

public class Priority {

    @SerializedName("id")
    @Expose
    private Long priorityId;
    @SerializedName("name")
    @Expose
    private String priorityName;

    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Long id) {
        this.priorityId = id;
    }

    public String getPriorityName() {
        return priorityName != null ? priorityName : "";
    }

    public void setPriorityName(String name) {
        this.priorityName = name;
    }
}
