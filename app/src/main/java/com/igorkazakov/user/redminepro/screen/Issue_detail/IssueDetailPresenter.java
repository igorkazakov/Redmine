package com.igorkazakov.user.redminepro.screen.Issue_detail;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Child;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.FixedVersion;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Priority;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortUser;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Tracker;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.dao.AttachmentEntityDAO;
import com.igorkazakov.user.redminepro.database.entity.AttachmentEntity;
import com.igorkazakov.user.redminepro.database.entity.IssueEntity;
import com.igorkazakov.user.redminepro.database.entity.PriorityEntity;
import com.igorkazakov.user.redminepro.database.entity.StatusEntity;
import com.igorkazakov.user.redminepro.database.entity.TrackerEntity;
import com.igorkazakov.user.redminepro.database.entity.UserEntity;
import com.igorkazakov.user.redminepro.database.entity.VersionEntity;
import com.igorkazakov.user.redminepro.database.realm.FixedVersionRealmDAO;
import com.igorkazakov.user.redminepro.database.realm.IssueRealmDAO;
import com.igorkazakov.user.redminepro.database.realm.ProjectPriorityRealmDAO;
import com.igorkazakov.user.redminepro.database.realm.ShortUserRealmDAO;
import com.igorkazakov.user.redminepro.database.realm.StatusRealmDAO;
import com.igorkazakov.user.redminepro.database.realm.TrackerRealmDAO;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.rxloader.LifecycleHandler;

/**
 * Created by user on 28.07.17.
 */

public class IssueDetailPresenter {

    private LifecycleHandler mLifecycleHandler;
    private IssueDetailView mView;

    public IssueDetailPresenter(@NonNull LifecycleHandler lifecycleHandler, @NonNull IssueDetailView issueDetailView) {
        mLifecycleHandler = lifecycleHandler;
        mView = issueDetailView;
    }

    public void tryLoadIssueDetailsData(long issueId) {

        RedmineRepository.getIssueDetails(issueId)
                .doOnSubscribe(mView::showLoading)
                .doOnTerminate(mView::hideLoading)
                .compose(mLifecycleHandler.reload(R.id.issue_details_request))
                .subscribe(issueEntity -> setupView(issueEntity),
                        throwable -> throwable.printStackTrace());
    }

    public void setupView(Issue issueEntity) {
        mView.setupView(issueEntity);
    }

    public List<Issue> getChildIssues(List<Child> children) {

        return IssueRealmDAO.getChildIssues(children);
    }

    public List<AttachmentEntity> getAttachments(IssueEntity issueEntity) {

        List<AttachmentEntity> attachmentEntities = new ArrayList<>();
        AttachmentEntityDAO attachmentEntityDAO = DatabaseManager.getDatabaseHelper().getAttachmentEntityDAO();

        try {

            attachmentEntities = attachmentEntityDAO.getAttachmentByParent(issueEntity.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return attachmentEntities;
    }

//    public List<Journal> getJournals(IssueEntity issueEntity) {
//
//        List<Journal> journalEntities = new ArrayList<>();
//        JournalEntityDAO journalEntityDAO = DatabaseManager.getDatabaseHelper().getJournalEntityDAO();
//
//        try {
//
//            journalEntities = journalEntityDAO.getjournalsByParent(issueEntity.getId());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return journalEntities;
//    }

//    public List<Detail> getJournalDetails(Journal journalEntity) {
//
//        List<DetailEntity> detailEntities = new ArrayList<>();
//        DetailEntityDAO detailEntityDAO = DatabaseManager.getDatabaseHelper().getDetailEntityDAO();
//        try {
//            detailEntities = detailEntityDAO.getDetailsByParent(journalEntity.getId());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return detailEntities;
//    }

    public ShortUser getUserById(long id) {

        return ShortUserRealmDAO.getUserById(id);
    }

    public Status getStatusById(long id) {

        return StatusRealmDAO.getStatusById(id);
    }

    public Tracker getTrackerById(long id) {

        return TrackerRealmDAO.getTrackerById(id);
    }

    public FixedVersion getVersionById(long id) {

        return FixedVersionRealmDAO.getFixedVersionById(id);
    }

    public Priority getPriorityById(long id) {

        return ProjectPriorityRealmDAO.getPriorityById(id);
    }
}
