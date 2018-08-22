package com.igorkazakov.user.redminepro.api.response;

import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.IssueDetail;
import com.igorkazakov.user.redminepro.database.room.entity.IssueEntity;

import java.util.List;

/**
 * Created by user on 13.07.17.
 */

public class IssuesResponse {


    @SerializedName("issues")
    private List<IssueEntity> mIssues;

    @SerializedName("issue")
    private IssueDetail mIssue;

    public List<IssueEntity> getIssues() {
        return mIssues;
    }

    public void setIssues(List<IssueEntity> mIssueList) {
        this.mIssues = mIssueList;
    }

    public IssueDetail getIssue() {
        return mIssue;
    }

    public void setIssue(IssueDetail mIssue) {
        this.mIssue = mIssue;
    }
}
