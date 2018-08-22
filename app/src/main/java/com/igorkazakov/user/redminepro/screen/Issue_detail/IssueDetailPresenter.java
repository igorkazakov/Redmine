package com.igorkazakov.user.redminepro.screen.Issue_detail;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.FixedVersion;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Priority;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortUser;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Tracker;
import com.igorkazakov.user.redminepro.database.room.entity.IssueDetailEntity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by user on 28.07.17.
 */

@InjectViewState
public class IssueDetailPresenter extends MvpPresenter<IssueDetailView> {

    private IssueDetailServiceInterface mRepository;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    public IssueDetailPresenter(IssueDetailServiceInterface redmineRepository) {
        mRepository = redmineRepository;
    }

    public void tryLoadIssueDetailsData(long issueId) {

        mDisposable.add(mRepository.getIssueDetails(issueId)
                .doOnSubscribe(__ -> getViewState().showLoading())
                .doOnTerminate(getViewState()::hideLoading)
                .subscribe(this::setupView,
                        throwable -> {
                            ApiException exception = (ApiException) throwable;
                            getViewState().showError(exception);
                        }));
    }

    public void setupView(IssueDetailEntity issueEntity) {
        getViewState().setupView(issueEntity);
    }

    public void checkChildIssues(Long issueDetailId) {

        mDisposable.add(mRepository.getChildsByIssueDetailId(issueDetailId)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(response -> mRepository.getChildIssues(response))
                .subscribe(result -> {

                    getViewState().setupChildIssues(result);
                }));
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
