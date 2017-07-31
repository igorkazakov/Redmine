package com.igorkazakov.user.redminepro.api.response;

import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;

import java.util.List;

/**
 * Created by user on 13.07.17.
 */

public class IssuesResponse {

    @SerializedName("issues")
    private List<Issue> mIssues;

    @SerializedName("issue")
    private Issue mIssue;

    public List<Issue> getIssues() {
        return mIssues;
    }

    public void setIssues(List<Issue> mIssueList) {
        this.mIssues = mIssueList;
    }

    public Issue getIssue() {
        return mIssue;
    }

    public void setIssue(Issue mIssue) {
        this.mIssue = mIssue;
    }
}
