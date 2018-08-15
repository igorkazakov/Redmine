package com.igorkazakov.user.redminepro.screen.Issue_detail;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

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

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by user on 28.07.17.
 */

@InjectViewState
public class IssueDetailPresenter extends MvpPresenter<IssueDetailView> {

    private IssueDetailServiceInterface mRepository;
    private Disposable mDisposable;

    public IssueDetailPresenter(IssueDetailServiceInterface redmineRepository) {
        mRepository = redmineRepository;
    }

    public void tryLoadIssueDetailsData(long issueId) {

        mDisposable = mRepository.getIssueDetails(issueId)
                .doOnSubscribe(__ -> getViewState().showLoading())
                .doOnTerminate(getViewState()::hideLoading)
                .subscribe(this::setupView,
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

        return mRepository.getChildIssues(children);
    }

    public ShortUser getUserById(long id) {

        return mRepository.getUserById(id);
    }

    public Status getStatusById(long id) {

        return mRepository.getStatusById(id);
    }

    public Tracker getTrackerById(long id) {

        return mRepository.getTrackerById(id);
    }

    public FixedVersion getVersionById(long id) {

        return mRepository.getVersionById(id);
    }

    public Priority getPriorityById(long id) {

        return mRepository.getPriorityById(id);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onActivityDestroy() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
