package com.igorkazakov.user.redminepro.screen.Issue_detail;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Child;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.FixedVersion;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.IssueDetail;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Namable;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Priority;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortUser;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Tracker;
import com.igorkazakov.user.redminepro.application.RedmineApplication;
import com.igorkazakov.user.redminepro.database.realm.FixedVersionDAO;
import com.igorkazakov.user.redminepro.database.realm.IssueDAO;
import com.igorkazakov.user.redminepro.database.realm.ProjectPriorityDAO;
import com.igorkazakov.user.redminepro.database.realm.ShortUserDAO;
import com.igorkazakov.user.redminepro.database.realm.StatusDAO;
import com.igorkazakov.user.redminepro.database.realm.TrackerDAO;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 28.07.17.
 */

@InjectViewState
public class IssueDetailPresenter extends MvpPresenter<IssueDetailView> {

    @Inject
    RedmineRepository mRedmineRepository;

    public IssueDetailPresenter() {
        RedmineApplication.getComponent().inject(this);
    }

    public void tryLoadIssueDetailsData(long issueId) {

        mRedmineRepository.getIssueDetails(issueId)
                .doOnSubscribe(__ -> getViewState().showLoading())
                .doOnTerminate(getViewState()::hideLoading)
                .subscribe(issueEntity -> setupView(issueEntity),
                        throwable -> {
                            ApiException exception = (ApiException)throwable;
                            getViewState().showError(exception);
                        });
    }

    public void setupView(IssueDetail issueEntity) {
        getViewState().setupView(issueEntity);
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
