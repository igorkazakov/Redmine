package com.igorkazakov.user.redminepro.screen.IssueDetail;

import com.igorkazakov.user.redminepro.database.entity.IssueEntity;
import com.igorkazakov.user.redminepro.screen.general.LoadingView;

/**
 * Created by user on 28.07.17.
 */

public interface IssueDetailView extends LoadingView {

    void setupView(IssueEntity issueEntity);
}
