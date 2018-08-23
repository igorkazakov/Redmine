package com.igorkazakov.user.redminepro.screen.Issue_detail;

import com.igorkazakov.user.redminepro.database.room.entity.AttachmentEntity;
import com.igorkazakov.user.redminepro.database.room.entity.ChildEntity;
import com.igorkazakov.user.redminepro.database.room.entity.DetailEntity;
import com.igorkazakov.user.redminepro.database.room.entity.FixedVersionEntity;
import com.igorkazakov.user.redminepro.database.room.entity.IssueDetailEntity;
import com.igorkazakov.user.redminepro.database.room.entity.IssueEntity;
import com.igorkazakov.user.redminepro.database.room.entity.JournalsEntity;
import com.igorkazakov.user.redminepro.database.room.entity.PriorityEntity;
import com.igorkazakov.user.redminepro.database.room.entity.ShortUserEntity;
import com.igorkazakov.user.redminepro.database.room.entity.StatusEntity;
import com.igorkazakov.user.redminepro.database.room.entity.TrackerEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface IssueDetailServiceInterface {

    Observable<IssueDetailEntity> getIssueDetails(long issueId);
    Single<List<IssueEntity>> getChildIssues(List<ChildEntity> children);
    Single<List<AttachmentEntity>> getAttachmentsByIssueDetailId(long issueDetailId);
    Single<List<JournalsEntity>> getJournalsByIssueDetailId(long issueDetailId);
    Single<List<ChildEntity>> getChildsByIssueDetailId(long issueDetailId);

    Single<List<DetailEntity>> getDetailEntitiesByJournalId(long journalId);

    ShortUserEntity getUserById(long id);
    StatusEntity getStatusById(long id);
    TrackerEntity getTrackerById(long id);
    FixedVersionEntity getVersionById(long id);
    PriorityEntity getPriorityById(long id);
}
