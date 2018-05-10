package com.igorkazakov.user.redminepro.screen.Issue_detail;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Child;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.FixedVersion;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Namable;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Priority;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortUser;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Tracker;
import com.igorkazakov.user.redminepro.database.realm.FixedVersionDAO;
import com.igorkazakov.user.redminepro.database.realm.IssueDAO;
import com.igorkazakov.user.redminepro.database.realm.ProjectPriorityDAO;
import com.igorkazakov.user.redminepro.database.realm.ShortUserDAO;
import com.igorkazakov.user.redminepro.database.realm.StatusDAO;
import com.igorkazakov.user.redminepro.database.realm.TrackerDAO;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;

import java.util.List;

import ru.arturvasilov.rxloader.LifecycleHandler;

/**
 * Created by user on 28.07.17.
 */

public class IssueDetailPresenter {

    private LifecycleHandler mLifecycleHandler;
    private IssueDetailView mView;

    public IssueDetailPresenter(@NonNull LifecycleHandler lifecycleHandler,
                                @NonNull IssueDetailView issueDetailView) {
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

    public String getSafeName(Namable object) {
        return object != null &&
                object.getName() != null ? object.getName() : "";
    }

    public List<Issue> getChildIssues(List<Child> children) {

        return IssueDAO.getChildIssues(children);
    }

    public ShortUser getUserById(long id) {

        return ShortUserDAO.getUserById(id);
    }

    public Status getStatusById(long id) {

        return StatusDAO.getStatusById(id);
    }

    public Tracker getTrackerById(long id) {

        return TrackerDAO.getTrackerById(id);
    }

    public FixedVersion getVersionById(long id) {

        return FixedVersionDAO.getFixedVersionById(id);
    }

    public Priority getPriorityById(long id) {

        return ProjectPriorityDAO.getPriorityById(id);
    }
}
