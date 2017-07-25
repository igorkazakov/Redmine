package com.igorkazakov.user.redminepro.screen.issues;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.models.IssueModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 25.07.17.
 */

public class IssueHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.projectNameLabel)
    TextView mProjectNameLabel;

    @BindView(R.id.issueNumberLabel)
    TextView mIssueNumberLabel;

    @BindView(R.id.issueSubjectLabel)
    TextView mIssueSubjectLabel;

    public IssueHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(IssueModel model) {

        mProjectNameLabel.setText(model.getProjectName());
        mIssueNumberLabel.setText(model.getIssueNumber());
        mIssueSubjectLabel.setText(model.getIssueSubject());
    }
}
