package com.igorkazakov.user.redminepro.api.responseEntity;

import com.igorkazakov.user.redminepro.database.room.entity.IssueEntity;
import com.igorkazakov.user.redminepro.database.room.entity.TimeEntryEntity;

import java.util.List;

/**
 * Created by user on 13.07.17.
 */

public class LoginAndTimeEntries {

    private List<IssueEntity> mIssueList;
    private List<TimeEntryEntity> mTimeEntryList;

    public LoginAndTimeEntries(List<IssueEntity> issueList, List<TimeEntryEntity> timeEntry) {
        mIssueList = issueList;
        mTimeEntryList = timeEntry;
    }
}
