package com.igorkazakov.user.redminepro.screen.issues;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;

import ru.arturvasilov.rxloader.LifecycleHandler;

/**
 * Created by user on 25.07.17.
 */

public class IssuesPresenter {

    private LifecycleHandler mLifecycleHandler;
    private IssuesView mView;

    public IssuesPresenter(@NonNull LifecycleHandler lifecycleHandler, @NonNull IssuesView view) {

        mLifecycleHandler = lifecycleHandler;
        mView = view;
    }

    public void tryLoadIssuesData() {

        RedmineRepository.getMyIssues()
                .doOnSubscribe(mView::showLoading)
                .doOnTerminate(mView::hideLoading)
                .compose(mLifecycleHandler.reload(R.id.calendar_days_request))
                .subscribe(response -> mView.setupView(),
                        throwable -> throwable.printStackTrace());
    }
}
