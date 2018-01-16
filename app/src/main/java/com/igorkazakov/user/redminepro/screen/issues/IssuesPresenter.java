package com.igorkazakov.user.redminepro.screen.issues;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.entity.IssueEntity;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;

import java.util.List;

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

        if (PreferenceUtils.getInstance().getIssuesDownloaded()) {
            List<IssueEntity> issueModels = DatabaseManager.getDatabaseHelper().getIssueEntityDAO().getMyIssues();
            mView.setupView(issueModels);

        } else {
            mView.showLoading();
        }

        RedmineRepository.getMyIssues()
                //.doOnSubscribe(mView::showLoading)
                .doOnTerminate(mView::hideLoading)
                .compose(mLifecycleHandler.reload(R.id.issues_request))
                .subscribe(response -> mView.setupView(response),
                        throwable -> throwable.printStackTrace());
    }
}
