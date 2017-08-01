package com.igorkazakov.user.redminepro.screen.IssueDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.entity.IssueEntity;
import com.igorkazakov.user.redminepro.screen.general.LoadingFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class IssueDetailActivity extends AppCompatActivity implements IssueDetailView {

    @BindView(R.id.contentView)
    FrameLayout mContentView;

    @BindView(R.id.statusTextView)
    TextView mStatusTextView;

    @BindView(R.id.priorityTextView)
    TextView mPriorityTextView;

    @BindView(R.id.assignedToTextView)
    TextView mAssignedToTextView;

    @BindView(R.id.trackerTextView)
    TextView mTrackerTextView;

    @BindView(R.id.fixedVersionTextView)
    TextView mFixedVersionTextView;

    @BindView(R.id.startDateTextView)
    TextView mStartDateTextView;

    @BindView(R.id.estimatedHoursTextView)
    TextView mEstimatedHoursTextView;

    @BindView(R.id.spentHoursTextView)
    TextView mSpentHoursTextView;

    @BindView(R.id.issueNameTextView)
    TextView mIssueNameTextView;

    @BindView(R.id.childIssuesList)
    RecyclerView mChildIssuesList;

    @BindView(R.id.attachmentList)
    RecyclerView mAttachmentList;

    @BindView(R.id.childIssueListView)
    View mChildIssueListView;

    @BindView(R.id.attachmentListView)
    View mAttachmentListView;


    private IssueDetailPresenter mPresenter;
    private LoadingFragment mLoadingView;
    public static final String ISSUE_ID_KEY = "ISSUE_ID_KEY";

    public static void start(@NonNull Context context, long issueId) {
        Intent intent = new Intent(context, IssueDetailActivity.class);
        intent.putExtra(ISSUE_ID_KEY, issueId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        mLoadingView = new LoadingFragment(this, mContentView);
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new IssueDetailPresenter(lifecycleHandler, this);
        long issueId = getIntent().getLongExtra(ISSUE_ID_KEY, 0);
        getSupportActionBar().setSubtitle("#" + String.valueOf(issueId));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mChildIssuesList.setLayoutManager(linearLayoutManager);
        mAttachmentList.setLayoutManager(linearLayoutManager);

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

        mStatusTextView.setText(issueEntity.getStatusName());
        mPriorityTextView.setText(issueEntity.getPriorityName());
        mAssignedToTextView.setText(issueEntity.getAssignedToName());
        mTrackerTextView.setText(issueEntity.getTrackerName());
        mFixedVersionTextView.setText(issueEntity.getFixedVersionName());
        mStartDateTextView.setText(issueEntity.getStartDate());
        mEstimatedHoursTextView.setText(String.valueOf(issueEntity.getEstimatedHours()));
        mSpentHoursTextView.setText(String.valueOf(issueEntity.getSpentHours()));
        mIssueNameTextView.setText(issueEntity.getSubject());

        List<IssueEntity> issueEntities = mPresenter.getChildIssues(issueEntity.getId());
        if (issueEntities.size() == 0) {
            mChildIssueListView.setVisibility(View.GONE);
        }
        ChildIssueAdapter adapter = new ChildIssueAdapter(issueEntities);
        mChildIssuesList.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
