package com.igorkazakov.user.redminepro.screen.issues;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.models.IssueModel;
import com.igorkazakov.user.redminepro.screen.IssueDetail.IssueDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 25.07.17.
 */

public class IssueHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.projectNameLabel)
    TextView mProjectNameLabel;

    @BindView(R.id.issueNumberLabel)
    TextView mIssueNumberLabel;

    @BindView(R.id.issueSubjectLabel)
    TextView mIssueSubjectLabel;

    private Context mContext;
    private IssueModel mIssueModel;

    public IssueHolder(View itemView, Context context) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
        mContext = context;
    }

    public void bind(IssueModel model) {

        mIssueModel = model;
        mProjectNameLabel.setText(model.getProjectName());
        mIssueNumberLabel.setText(model.getIssueNumber());
        mIssueSubjectLabel.setText(model.getIssueSubject());
    }

    @Override
    public void onClick(View view) {
        IssueDetailActivity.start(mContext, mIssueModel.getId());
    }
}
