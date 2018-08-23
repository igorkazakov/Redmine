package com.igorkazakov.user.redminepro.screen.issues;

import com.igorkazakov.user.redminepro.database.room.entity.IssueEntity;
import com.igorkazakov.user.redminepro.screen.base.ErrorInterface;
import com.igorkazakov.user.redminepro.screen.base.ProgressInterface;

import java.util.List;

/**
 * Created by user on 25.07.17.
 */

public interface IssuesView extends ProgressInterface, ErrorInterface {

    void setupView(List<IssueEntity> issueModels);
}
