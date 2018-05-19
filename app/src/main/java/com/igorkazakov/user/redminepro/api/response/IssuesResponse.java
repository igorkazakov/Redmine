package com.igorkazakov.user.redminepro.api.response;

import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.IssueDetail;

import java.util.List;

/**
 * Created by user on 13.07.17.
 */

public class IssuesResponse {

    @SerializedName("issues")
    private List<Issue> mIssues;

    @SerializedName("issue")
    private IssueDetail mIssue;

    public List<Issue> getIssues() {
        return mIssues;
    }

    public void setIssues(List<Issue> mIssueList) {
        this.mIssues = mIssueList;
    }

    public IssueDetail getIssue() {
        return mIssue;
    }

    public void setIssue(IssueDetail mIssue) {
        this.mIssue = mIssue;
    }
}
