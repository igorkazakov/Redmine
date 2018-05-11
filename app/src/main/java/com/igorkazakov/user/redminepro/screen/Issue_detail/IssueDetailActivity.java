package com.igorkazakov.user.redminepro.screen.Issue_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Attachment;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Journal;
import com.igorkazakov.user.redminepro.screen.base.LoadingFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IssueDetailActivity extends MvpAppCompatActivity implements IssueDetailView {

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

    @BindView(R.id.journalIssuesList)
    RecyclerView mJournalIssuesList;

    @BindView(R.id.childIssueListView)
    View mChildIssueListView;

    @BindView(R.id.attachmentListView)
    View mAttachmentListView;

    @BindView(R.id.journalListView)
    View mJournalListView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectPresenter
    public IssueDetailPresenter mPresenter;
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

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLoadingView = new LoadingFragment(this, mContentView);

        long issueId = getIntent().getLongExtra(ISSUE_ID_KEY, 0);
        getSupportActionBar().setSubtitle("#" + String.valueOf(issueId));

        setupChildIssueList();
        setupAttachmentIssueList();
        setupJournalIssueList();
        mPresenter.tryLoadIssueDetailsData(issueId);
    }

    private void setupChildIssueList() {
        mChildIssuesList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    private void setupAttachmentIssueList() {
        mAttachmentList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    private void setupJournalIssueList() {
        mJournalIssuesList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
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
    public void setupView(Issue issueEntity) {

        mStatusTextView.setText(mPresenter.getSafeName(issueEntity.getStatus()));
        mPriorityTextView.setText(mPresenter.getSafeName(issueEntity.getPriority()));
        mAssignedToTextView.setText(mPresenter.getSafeName(issueEntity.getAssignedTo()));
        mTrackerTextView.setText(mPresenter.getSafeName(issueEntity.getTracker()));
        mFixedVersionTextView.setText(mPresenter.getSafeName(issueEntity.getFixedVersion()));
        mStartDateTextView.setText(issueEntity.getStartDate());
        mEstimatedHoursTextView.setText(String.valueOf(issueEntity.getEstimatedHours()));
        mSpentHoursTextView.setText(String.valueOf(issueEntity.getSpentHours()));
        mIssueNameTextView.setText(issueEntity.getSubject());

        if (issueEntity.getChildren() != null) {
            List<Issue> issueEntities = mPresenter.getChildIssues(issueEntity.getChildren());
            if (issueEntities.size() == 0) {
                mChildIssueListView.setVisibility(View.GONE);
            }

            ChildIssueAdapter adapter = new ChildIssueAdapter(issueEntities);
            mChildIssuesList.setAdapter(adapter);

        } else {
            mChildIssueListView.setVisibility(View.GONE);
        }

        List<Attachment> attachmentEntities = issueEntity.getAttachments();
        if (attachmentEntities.size() == 0) {
            mAttachmentListView.setVisibility(View.GONE);
        }
        AttachmentAdapter attachmentAdapter = new AttachmentAdapter(attachmentEntities);
        mAttachmentList.setAdapter(attachmentAdapter);

        List<Journal> journalEntities = issueEntity.getJournals();
        if (journalEntities.size() == 0) {
            mJournalListView.setVisibility(View.GONE);
        }
        JournalAdapter journalAdapter = new JournalAdapter(journalEntities, mPresenter);
        mJournalIssuesList.setAdapter(journalAdapter);

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
