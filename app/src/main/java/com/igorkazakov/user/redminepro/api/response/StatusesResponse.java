package com.igorkazakov.user.redminepro.api.response;

import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;

import java.util.List;

/**
 * Created by user on 09.08.17.
 */

public class StatusesResponse {

    @SerializedName("issue_statuses")
    private List<Status> statuses;

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }
}
