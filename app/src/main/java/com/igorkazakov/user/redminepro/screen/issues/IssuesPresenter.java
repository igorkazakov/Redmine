package com.igorkazakov.user.redminepro.screen.issues;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.igorkazakov.user.redminepro.api.ApiException;

import io.reactivex.disposables.Disposable;

/**
 * Created by user on 25.07.17.
 */

@InjectViewState
public class IssuesPresenter extends MvpPresenter<IssuesView> {

    private IssuesServiceInterface mRepository;
    private Disposable mDisposable;

    public IssuesPresenter(IssuesServiceInterface redmineRepository) {
        mRepository = redmineRepository;
    }
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        tryLoadIssuesData();
    }

    public void tryLoadIssuesData() {

        mDisposable = mRepository.getMyIssues()
                .doOnSubscribe(__ -> getViewState().showLoading())
                .doOnTerminate(getViewState()::hideLoading)
                .subscribe(response -> getViewState().setupView(response),
                        throwable -> {
                            ApiException exception = (ApiException)throwable;
                            getViewState().showError(exception);
                        });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onActivityDestroy() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
