package com.igorkazakov.user.redminepro.screen.issues;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.models.IssueModel;
import com.igorkazakov.user.redminepro.screen.general.LoadingFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class IssuesActivity extends AppCompatActivity implements IssuesView {


    @BindView(R.id.contentView)
    ConstraintLayout mContentView;

    @BindView(R.id.issuesList)
    RecyclerView mIssueList;

    private IssuesPresenter mPresenter;
    private LoadingFragment mLoadingView;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, IssuesActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mLoadingView = new LoadingFragment(this, mContentView);
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new IssuesPresenter(lifecycleHandler, this);
        mPresenter.tryLoadIssuesData();
    }

    @Override
    public void setupView() {

        List<IssueModel> issueModels = DatabaseManager.getDatabaseHelper().getIssueEntityDAO().getMyIssues();
        IssuesAdapter adapter = new IssuesAdapter(issueModels);
        mIssueList.setAdapter(adapter);
    }

    @Override
    public void showLoading() {
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hideLoading();
    }
}
