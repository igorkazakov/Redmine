package com.igorkazakov.user.redminepro.screen.issues;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.screen.general.LoadingView;

import java.util.List;

/**
 * Created by user on 25.07.17.
 */

public interface IssuesView extends LoadingView {

    void setupView(List<Issue> issueModels);
}
