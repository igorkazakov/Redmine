package com.igorkazakov.user.redminepro.models;

import android.support.annotation.NonNull;

/**
 * Created by user on 25.07.17.
 */

public class IssueModel {

    private long mId;
    private String mProjectName;
    private String mIssueSubject;
    private String mIssueNumber;

    public String getProjectName() {
        return mProjectName;
    }

    public String getIssueSubject() {
        return mIssueSubject;
    }

    public String getIssueNumber() {
        return mIssueNumber;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public IssueModel(long issueId, @NonNull String projectName, @NonNull String issueNumber, @NonNull String issueSubject) {
        mProjectName = projectName;
        mIssueNumber = issueNumber;
        mIssueSubject = issueSubject;
        mId = issueId;
    }
}
