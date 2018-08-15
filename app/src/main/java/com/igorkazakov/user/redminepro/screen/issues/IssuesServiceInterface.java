package com.igorkazakov.user.redminepro.screen.issues;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;

import java.util.List;

import io.reactivex.Observable;

public interface IssuesServiceInterface {

    Observable<List<Issue>> getMyIssues();
}
