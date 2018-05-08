package com.igorkazakov.user.redminepro.screen.Issue_detail;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.screen.general.LoadingView;

/**
 * Created by user on 28.07.17.
 */

public interface IssueDetailView extends LoadingView {

    void setupView(Issue issueEntity);
}
