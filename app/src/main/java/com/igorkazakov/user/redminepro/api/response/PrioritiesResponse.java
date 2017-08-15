package com.igorkazakov.user.redminepro.api.response;

import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Priority;

import java.util.List;

/**
 * Created by user on 15.08.17.
 */

public class PrioritiesResponse {

    @SerializedName("issue_priorities")
    private List<Priority> priorities;

    public List<Priority> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<Priority> priorities) {
        this.priorities = priorities;
    }
}
