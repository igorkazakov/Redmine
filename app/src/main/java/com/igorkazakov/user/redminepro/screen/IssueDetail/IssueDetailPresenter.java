package com.igorkazakov.user.redminepro.screen.IssueDetail;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.dao.AttachmentEntityDAO;
import com.igorkazakov.user.redminepro.database.dao.DetailEntityDAO;
import com.igorkazakov.user.redminepro.database.dao.IssueEntityDAO;
import com.igorkazakov.user.redminepro.database.entity.AttachmentEntity;
import com.igorkazakov.user.redminepro.database.entity.DetailEntity;
import com.igorkazakov.user.redminepro.database.entity.IssueEntity;
import com.igorkazakov.user.redminepro.database.entity.JournalEntity;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                .subscribe(issueEntity -> mView.setupView(issueEntity),
                        Throwable::printStackTrace);
    }

    public List<IssueEntity> getChildIssues(IssueEntity issueEntity) {

        List<IssueEntity> issueEntityList = new ArrayList<>();
        IssueEntityDAO issueDao = DatabaseManager.getDatabaseHelper().getIssueEntityDAO();

        try {
            Set<Long> idsSet = new HashSet<>();
            for (long id : issueEntity.getChildrenIds()) {

                IssueEntity issue = issueDao.queryForId(id);
                if (issue != null) {
                    issueEntityList.add(issue);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return issueEntityList;
    }

    public List<AttachmentEntity> getAttachments(IssueEntity issueEntity) {

        List<AttachmentEntity> attachmentEntities = new ArrayList<>();
        AttachmentEntityDAO attachmentEntityDAO = DatabaseManager.getDatabaseHelper().getAttachmentEntityDAO();

        try {

            for (long id : issueEntity.getAttachmentIds()) {

                AttachmentEntity attachmentEntity = attachmentEntityDAO.queryForId(id);
                if (attachmentEntity != null) {
                    attachmentEntities.add(attachmentEntity);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attachmentEntities;
    }

    public List<JournalEntity> getJournals(IssueEntity issueEntity) {

        List<JournalEntity> journalEntities = new ArrayList<>();

        try {

            for (long id : issueEntity.getJournalIds()) {
                JournalEntity journalEntity = DatabaseManager.
                        getDatabaseHelper().
                        getJournalEntityDAO()
                        .queryForId(id);

                if (journalEntity != null) {
                    journalEntities.add(journalEntity);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return journalEntities;
    }

    public static List<DetailEntity> getJournalDetails(JournalEntity journalEntity) {

        List<DetailEntity> detailEntities = new ArrayList<>();
        DetailEntityDAO detailEntityDAO = DatabaseManager.getDatabaseHelper().getDetailEntityDAO();
        try {
            for (long id : journalEntity.getDetails()) {

                DetailEntity detailEntity = detailEntityDAO.queryForId(id);
                if (detailEntity != null) {
                    detailEntities.add(detailEntity);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detailEntities;
    }

}
