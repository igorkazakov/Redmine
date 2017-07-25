package com.igorkazakov.user.redminepro.api.responseEntity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.TimeEntry;

import java.util.List;

/**
 * Created by user on 13.07.17.
 */

public class LoginAndTimeEntries {

    private List<Issue> mIssueList;
    private List<TimeEntry> mTimeEntryList;

    public LoginAndTimeEntries(List<Issue> issueList, List<TimeEntry> timeEntry) {
        mIssueList = issueList;
        mTimeEntryList = timeEntry;
    }
}
