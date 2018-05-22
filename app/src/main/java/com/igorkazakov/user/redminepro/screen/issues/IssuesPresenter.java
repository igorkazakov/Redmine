package com.igorkazakov.user.redminepro.screen.issues;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;

/**
 * Created by user on 25.07.17.
 */

@InjectViewState
public class IssuesPresenter extends MvpPresenter<IssuesView> {

    RedmineRepository mRedmineRepository;

    public IssuesPresenter(RedmineRepository redmineRepository) {
        mRedmineRepository = redmineRepository;
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
