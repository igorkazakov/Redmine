package com.igorkazakov.user.redminepro.screen.issues;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.application.RedmineApplication;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;

import javax.inject.Inject;

/**
 * Created by user on 25.07.17.
 */

@InjectViewState
public class IssuesPresenter extends MvpPresenter<IssuesView> {

    @Inject
    RedmineRepository mRedmineRepository;

    public IssuesPresenter() {
        RedmineApplication.getComponent().inject(this);
    }
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        tryLoadIssuesData();
    }

    public void tryLoadIssuesData() {

        mRedmineRepository.getMyIssues()
                .doOnSubscribe(__ -> getViewState().showLoading())
                .doOnTerminate(getViewState()::hideLoading)
                .subscribe(response -> getViewState().setupView(response),
                        throwable -> {
                            ApiException exception = (ApiException)throwable;
                            getViewState().showError(exception);
                        });
    }
}
