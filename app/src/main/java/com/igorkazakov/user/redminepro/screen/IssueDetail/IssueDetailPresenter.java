package com.igorkazakov.user.redminepro.screen.IssueDetail;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.dao.IssueEntityDAO;
import com.igorkazakov.user.redminepro.database.entity.AttachmentEntity;
import com.igorkazakov.user.redminepro.database.entity.ChildEntity;
import com.igorkazakov.user.redminepro.database.entity.IssueEntity;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;

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
            for (ChildEntity childEntity : issueEntity.getChildren()) {
                idsSet.add(childEntity.getId());
            }
            issueEntityList = issueDao.getIssuesByIds(idsSet);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return issueEntityList;
    }

    public List<AttachmentEntity> getAttachments(IssueEntity issueEntity) {

        List<AttachmentEntity> attachmentEntities = new ArrayList<>();

        for (AttachmentEntity attachmentEntity : issueEntity.getAttachments()) {
            attachmentEntities.add(attachmentEntity);
        }

        return attachmentEntities;
    }
}
