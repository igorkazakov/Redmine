package com.igorkazakov.user.redminepro.screen.IssueDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.entity.DetailEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 03.08.17.
 */

public class JournalDetailHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.detailTextView)
    TextView mDetailTextView;

    private Context mContext;
    private DetailEntity mDetailEntity;

    public JournalDetailHolder(View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
    }

    public void bind(DetailEntity entity) {

        mDetailEntity = entity;
        String string = "";
        String prefixStr = "";

        if (mDetailEntity.getProperty().equalsIgnoreCase("attachment")) {
            prefixStr = "<b>File</b> ";

            string = string.concat(prefixStr);

            if (mDetailEntity.getNewValue() != null) {
                string = string.concat(mDetailEntity.getNewValue() + " <b>added</b>");

            } else {
                string = string.concat(mDetailEntity.getOldValue() + " <b>deleted</b>");
            }

        } else {

            switch (mDetailEntity.getName()) {
                case "parent_id":
                    prefixStr = "<b>Parent task</b> ";
                    break;

                case "tracker_id":
                    prefixStr = "<b>Tracker</b> ";
                    break;

                case "status_id":
                    prefixStr = "<b>Status</b> ";
                    break;

                case "fixed_version_id":
                    prefixStr = "<b>Target version</b> ";
                    break;

                case "subject":
                    prefixStr = "<b>Subject</b> ";
                    break;

                case "assigned_to_id":
                    prefixStr = "<b>Assignee</b> ";
                    break;

                default:
                    prefixStr = "";
            }

            string = string.concat(prefixStr);

            if (mDetailEntity.getNewValue() != null && mDetailEntity.getOldValue() != null) {

                string = string.concat("changed from " + "<b>" + mDetailEntity.getOldValue() + "</b>" + " to " + "<b>" + mDetailEntity.getNewValue() + "</b>");

            } else if (mDetailEntity.getNewValue() != null) {

                string = string.concat("set to " + "<b>" + mDetailEntity.getNewValue() + "</b>");
            }
        }

        mDetailTextView.setText(Html.fromHtml(string));
    }
}