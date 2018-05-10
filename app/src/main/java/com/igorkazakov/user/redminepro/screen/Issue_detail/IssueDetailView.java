package com.igorkazakov.user.redminepro.screen.Issue_detail;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.screen.base.BaseViewInterface;

/**
 * Created by user on 28.07.17.
 */

public interface IssueDetailView extends BaseViewInterface {

    void setupView(Issue issueEntity);
}
