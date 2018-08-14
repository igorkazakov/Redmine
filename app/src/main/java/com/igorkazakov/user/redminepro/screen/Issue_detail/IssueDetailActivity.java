package com.igorkazakov.user.redminepro.screen.Issue_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Attachment;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.IssueDetail;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Journal;
import com.igorkazakov.user.redminepro.application.RedmineApplication;
import com.igorkazakov.user.redminepro.repository.RedmineService;
import com.igorkazakov.user.redminepro.repository.RepositoryInterface;
import com.igorkazakov.user.redminepro.screen.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class IssueDetailActivity extends BaseActivity implements IssueDetailView {

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

    @Inject
    RepositoryInterface mRepository;

    @InjectPresenter
    public IssueDetailPresenter mPresenter;
    public static final String ISSUE_ID_KEY = "ISSUE_ID_KEY";

    @ProvidePresenter
    IssueDetailPresenter provideIssueDetailPresenter() {
        return new IssueDetailPresenter(mRepository);
    }

    @Override
    public MvpDelegate getMvpDelegate() {
        RedmineApplication.getComponent().inject(this);
        return super.getMvpDelegate();
    }

    public static void start(@NonNull Context context, long issueId) {
        Intent intent = new Intent(context, IssueDetailActivity.class);
        intent.putExtra(ISSUE_ID_KEY, issueId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        long issueId = getIntent().getLongExtra(ISSUE_ID_KEY, 0);
        getSupportActionBar().setSubtitle("#" + String.valueOf(issueId));

        setupChildIssueList();
        setupAttachmentIssueList();
        setupJournalIssueList();
        mPresenter.tryLoadIssueDetailsData(issueId);
    }

    @Override
    public int getMainContentLayout() {
        return R.layout.content_issue_detail;
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
    public void setupView(IssueDetail issueEntity) {

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

            } else {
                mChildIssueListView.setVisibility(View.VISIBLE);
            }

            ChildIssueAdapter adapter = new ChildIssueAdapter(issueEntities);
            mChildIssuesList.setAdapter(adapter);

        } else {
            mChildIssueListView.setVisibility(View.GONE);
        }

        List<Attachment> attachmentEntities = issueEntity.getAttachments();
        if (attachmentEntities.size() == 0) {
            mAttachmentListView.setVisibility(View.GONE);

        } else {
            mAttachmentListView.setVisibility(View.VISIBLE);
        }

        AttachmentAdapter attachmentAdapter = new AttachmentAdapter(attachmentEntities);
        mAttachmentList.setAdapter(attachmentAdapter);

        List<Journal> journalEntities = issueEntity.getJournals();
        if (journalEntities.size() == 0) {
            mJournalListView.setVisibility(View.GONE);

        } else {
            mJournalListView.setVisibility(View.VISIBLE);
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
