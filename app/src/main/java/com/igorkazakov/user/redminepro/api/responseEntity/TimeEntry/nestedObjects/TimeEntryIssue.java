package com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects;

/**
 * Created by user on 13.07.17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeEntryIssue {

    @SerializedName("id")
    @Expose
    private Integer issueId;

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer id) {
        this.issueId = id;
    }
}
