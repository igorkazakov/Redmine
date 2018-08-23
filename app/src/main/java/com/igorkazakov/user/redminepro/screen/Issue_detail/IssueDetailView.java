package com.igorkazakov.user.redminepro.screen.Issue_detail;

import com.igorkazakov.user.redminepro.database.room.entity.AttachmentEntity;
import com.igorkazakov.user.redminepro.database.room.entity.IssueDetailEntity;
import com.igorkazakov.user.redminepro.database.room.entity.IssueEntity;
import com.igorkazakov.user.redminepro.database.room.entity.JournalsEntity;
import com.igorkazakov.user.redminepro.screen.base.ErrorInterface;
import com.igorkazakov.user.redminepro.screen.base.ProgressInterface;

import java.util.List;

/**
 * Created by user on 28.07.17.
 */

public interface IssueDetailView extends ProgressInterface, ErrorInterface {

    void setupView(IssueDetailEntity issueEntity);

    void setupChildIssues(List<IssueEntity> issueEntities);

    void setupAttachments(List<AttachmentEntity> attachmentEntities);

    void setupJournals(List<JournalsEntity> journalsEntities);
}
