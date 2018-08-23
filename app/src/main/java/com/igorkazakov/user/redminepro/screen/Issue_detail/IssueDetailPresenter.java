package com.igorkazakov.user.redminepro.screen.Issue_detail;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.database.room.entity.DetailEntity;
import com.igorkazakov.user.redminepro.database.room.entity.FixedVersionEntity;
import com.igorkazakov.user.redminepro.database.room.entity.IssueDetailEntity;
import com.igorkazakov.user.redminepro.database.room.entity.PriorityEntity;
import com.igorkazakov.user.redminepro.database.room.entity.ShortUserEntity;
import com.igorkazakov.user.redminepro.database.room.entity.StatusEntity;
import com.igorkazakov.user.redminepro.database.room.entity.TrackerEntity;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

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
                            if (throwable.getClass().isInstance(ApiException.class)) {
                                ApiException exception = (ApiException) throwable;
                                getViewState().showError(exception);
                            }
                        }));
    }

    public void setupView(IssueDetailEntity issueEntity) {
        if (!issueEntity.isEmpty()) {
            getViewState().setupView(issueEntity);
        }
    }

    public void checkChildIssues(Long issueDetailId) {

        mDisposable.add(mRepository.getChildsByIssueDetailId(issueDetailId)
                .flatMap(response -> mRepository.getChildIssues(response))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {

                    getViewState().setupChildIssues(result);
                }));
    }

    public void checkAttachments(Long issueDetailId) {

        mDisposable.add(mRepository.getAttachmentsByIssueDetailId(issueDetailId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {

                    getViewState().setupAttachments(result);
                }));
    }

    public void checkJournals(Long issueDetailId) {

        mDisposable.add(mRepository.getJournalsByIssueDetailId(issueDetailId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {

                    getViewState().setupJournals(result);
                }));
    }

    public void fetchDetails(long journalId, Consumer<? super List<DetailEntity>> onSuccess) {

        mDisposable.add(mRepository.getDetailEntitiesByJournalId(journalId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess));
    }

    public ShortUserEntity getUserById(long id) {

        return mRepository.getUserById(id);
    }

    public StatusEntity getStatusById(long id) {

        return mRepository.getStatusById(id);
    }

    public TrackerEntity getTrackerById(long id) {

        return mRepository.getTrackerById(id);
    }

    public FixedVersionEntity getVersionById(long id) {

        return mRepository.getVersionById(id);
    }

    public PriorityEntity getPriorityById(long id) {

        return mRepository.getPriorityById(id);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onActivityDestroy() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
