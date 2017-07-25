package com.igorkazakov.user.redminepro.models;

import android.support.annotation.NonNull;

/**
 * Created by user on 25.07.17.
 */

public class IssueModel {

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

    public IssueModel(@NonNull String projectName, @NonNull String issueNumber, @NonNull String issueSubject) {
        mProjectName = projectName;
        mIssueNumber = issueNumber;
        mIssueSubject = issueSubject;
    }
}
