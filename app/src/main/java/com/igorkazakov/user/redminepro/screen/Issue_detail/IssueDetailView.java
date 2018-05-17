package com.igorkazakov.user.redminepro.screen.Issue_detail;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.screen.base.ErrorInterface;
import com.igorkazakov.user.redminepro.screen.base.ProgressInterface;

/**
 * Created by user on 28.07.17.
 */

public interface IssueDetailView extends ProgressInterface, ErrorInterface {

    void setupView(Issue issueEntity);
}
