package com.igorkazakov.user.redminepro.api.response;

import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.database.room.entity.StatusEntity;

import java.util.List;

/**
 * Created by user on 09.08.17.
 */

public class StatusesResponse {

    @SerializedName("issue_statuses")
    private List<StatusEntity> statuses;

    public List<StatusEntity> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<StatusEntity> statuses) {
        this.statuses = statuses;
    }
}
