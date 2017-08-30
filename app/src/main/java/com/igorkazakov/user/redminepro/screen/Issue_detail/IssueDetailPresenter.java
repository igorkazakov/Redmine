package com.igorkazakov.user.redminepro.screen.Issue_detail;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.dao.AttachmentEntityDAO;
import com.igorkazakov.user.redminepro.database.dao.DetailEntityDAO;
import com.igorkazakov.user.redminepro.database.dao.IssueEntityDAO;
import com.igorkazakov.user.redminepro.database.dao.JournalEntityDAO;
import com.igorkazakov.user.redminepro.database.entity.AttachmentEntity;
import com.igorkazakov.user.redminepro.database.entity.DetailEntity;
import com.igorkazakov.user.redminepro.database.entity.IssueEntity;
import com.igorkazakov.user.redminepro.database.entity.JournalEntity;
import com.igorkazakov.user.redminepro.database.entity.PriorityEntity;
import com.igorkazakov.user.redminepro.database.entity.StatusEntity;
import com.igorkazakov.user.redminepro.database.entity.TrackerEntity;
import com.igorkazakov.user.redminepro.database.entity.UserEntity;
import com.igorkazakov.user.redminepro.database.entity.VersionEntity;
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
                        Throwable::printStackTrace);
    }

    public void setupView(IssueEntity issueEntity) {
        mView.setupView(issueEntity);
    }

    public List<IssueEntity> getChildIssues(IssueEntity issueEntity) {

        List<IssueEntity> issueEntityList = new ArrayList<>();
        IssueEntityDAO issueDao = DatabaseManager.getDatabaseHelper().getIssueEntityDAO();

        try {
            issueEntityList = issueDao.getChildIssues(issueEntity.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return issueEntityList;
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

    public List<JournalEntity> getJournals(IssueEntity issueEntity) {

        List<JournalEntity> journalEntities = new ArrayList<>();
        JournalEntityDAO journalEntityDAO = DatabaseManager.getDatabaseHelper().getJournalEntityDAO();

        try {

            journalEntities = journalEntityDAO.getjournalsByParent(issueEntity.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return journalEntities;
    }

    public List<DetailEntity> getJournalDetails(JournalEntity journalEntity) {

        List<DetailEntity> detailEntities = new ArrayList<>();
        DetailEntityDAO detailEntityDAO = DatabaseManager.getDatabaseHelper().getDetailEntityDAO();
        try {
            detailEntities = detailEntityDAO.getDetailsByParent(journalEntity.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return detailEntities;
    }

    public UserEntity getUserById(long id) {
        UserEntity entity = null;
        try {
            entity = DatabaseManager.getDatabaseHelper().getUserEntityDAO().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public StatusEntity getStatusById(long id) {
        StatusEntity entity = null;
        try {
            entity = DatabaseManager.getDatabaseHelper().getStatusEntityDAO().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public TrackerEntity getTrackerById(long id) {
        TrackerEntity entity = null;
        try {
            entity = DatabaseManager.getDatabaseHelper().getTrackerEntityDAO().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public VersionEntity getVersionById(long id) {
        VersionEntity entity = null;
        try {
            entity = DatabaseManager.getDatabaseHelper().getVersionEntityDAO().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public PriorityEntity getPriorityById(long id) {
        PriorityEntity entity = null;
        try {
            entity = DatabaseManager.getDatabaseHelper().getPriorityEntityDAO().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }
}
