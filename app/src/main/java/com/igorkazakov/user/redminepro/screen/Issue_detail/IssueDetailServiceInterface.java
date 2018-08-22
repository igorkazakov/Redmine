package com.igorkazakov.user.redminepro.screen.Issue_detail;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.FixedVersion;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Priority;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortUser;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Tracker;
import com.igorkazakov.user.redminepro.database.room.entity.ChildEntity;
import com.igorkazakov.user.redminepro.database.room.entity.IssueDetailEntity;
import com.igorkazakov.user.redminepro.database.room.entity.IssueEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface IssueDetailServiceInterface {

    Observable<IssueDetailEntity> getIssueDetails(long issueId);
    Single<List<IssueEntity>> getChildIssues(List<ChildEntity> children);

    Single<List<ChildEntity>> getChildsByIssueDetailId(long issueDetailId);

    ShortUser getUserById(long id);
    Status getStatusById(long id);
    Tracker getTrackerById(long id);
    FixedVersion getVersionById(long id);
    Priority getPriorityById(long id);
}
