package com.igorkazakov.user.redminepro.screen.issues;

import com.igorkazakov.user.redminepro.database.room.entity.IssueEntity;

import java.util.List;

import io.reactivex.Observable;

public interface IssuesServiceInterface {

    Observable<List<IssueEntity>> getMyIssues();
}
