package com.igorkazakov.user.redminepro.api.response;

import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.database.room.entity.PriorityEntity;

import java.util.List;

/**
 * Created by user on 15.08.17.
 */

public class PrioritiesResponse {

    @SerializedName("issue_priorities")
    private List<PriorityEntity> priorities;

    public List<PriorityEntity> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<PriorityEntity> priorities) {
        this.priorities = priorities;
    }
}
