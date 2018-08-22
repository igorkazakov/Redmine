package com.igorkazakov.user.redminepro.screen.Issue_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.room.entity.IssueEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 01.08.17.
 */

public class ChildIssueHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.titleTextView)
    TextView mTitleTextView;

    @BindView(R.id.trackerTextView)
    TextView mTrackerTextView;

    @BindView(R.id.assignedToTextView)
    TextView mAssignedToTextView;

    @BindView(R.id.statusTextView)
    TextView mStatusTextView;

    private Context mContext;
    private IssueEntity mIssueEntity;

    public ChildIssueHolder(View itemView, Context context) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
        mContext = context;
    }

    public void bind(IssueEntity entity) {

        mIssueEntity = entity;
        mTitleTextView.setText(entity.getSubject());
        mTrackerTextView.setText(entity.getTracker().getName());
        mAssignedToTextView.setText(entity.getAssignedTo().getName());
        mStatusTextView.setText(entity.getStatus().getName());
    }

    @Override
    public void onClick(View view) {
        IssueDetailActivity.start(mContext, mIssueEntity.getId());
    }
}