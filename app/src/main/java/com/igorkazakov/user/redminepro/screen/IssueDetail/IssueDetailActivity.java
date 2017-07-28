package com.igorkazakov.user.redminepro.screen.IssueDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.entity.IssueEntity;
import com.igorkazakov.user.redminepro.screen.general.LoadingFragment;
import com.igorkazakov.user.redminepro.screen.issues.IssuesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class IssueDetailActivity extends AppCompatActivity implements IssueDetailView {

    @BindView(R.id.contentView)
    FrameLayout mContentView;

    private IssueDetailPresenter mPresenter;
    private LoadingFragment mLoadingView;
    public static final String ISSUE_ID_KEY = "ISSUE_ID_KEY";

    public static void start(@NonNull Context context, long issueId) {
        Intent intent = new Intent(context, IssuesActivity.class);
        intent.putExtra(ISSUE_ID_KEY, issueId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        mLoadingView = new LoadingFragment(this, mContentView);
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new IssueDetailPresenter(lifecycleHandler, this);
        long issueId = getIntent().getLongExtra(ISSUE_ID_KEY, 0);
        mPresenter.tryLoadIssueDetailsData(issueId);
    }

    @Override
    public void showLoading() {
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hideLoading();
    }

    @Override
    public void setupView(IssueEntity issueEntity) {

    }
}
