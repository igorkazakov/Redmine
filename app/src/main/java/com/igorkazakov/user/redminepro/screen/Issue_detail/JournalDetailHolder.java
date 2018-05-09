package com.igorkazakov.user.redminepro.screen.Issue_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Detail;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.FixedVersion;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Priority;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortUser;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Tracker;
import com.igorkazakov.user.redminepro.database.entity.PriorityEntity;
import com.igorkazakov.user.redminepro.database.entity.StatusEntity;
import com.igorkazakov.user.redminepro.database.entity.TrackerEntity;
import com.igorkazakov.user.redminepro.database.entity.UserEntity;
import com.igorkazakov.user.redminepro.database.entity.VersionEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 03.08.17.
 */

public class JournalDetailHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.detailTextView)
    TextView mDetailTextView;

    private Context mContext;
    private Detail mDetailEntity;

    public JournalDetailHolder(View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
    }

    public void bind(Detail entity, IssueDetailPresenter issueDetailPresenter) {

        mDetailEntity = entity;
        String string = "";
        String prefixStr = "";
        String newValue = "";
        String oldValue = "";

        if (mDetailEntity.getProperty().equalsIgnoreCase("attr")) {
            switch (mDetailEntity.getName()) {
                case "assigned_to_id": {
                    if (mDetailEntity.getNewValue() != null) {
                        ShortUser user = issueDetailPresenter.
                                getUserById(Long.valueOf(mDetailEntity.getNewValue()));

                        if (user != null) {
                            newValue = user.getName();
                        }
                    }

                    if (mDetailEntity.getOldValue() != null) {
                        ShortUser user = issueDetailPresenter.
                                getUserById(Long.valueOf(mDetailEntity.getOldValue()));

                        if (user != null) {
                            oldValue = user.getName();
                        }
                    }
                    break;
                }

                case "fixed_version_id": {
                    if (mDetailEntity.getNewValue() != null) {
                        FixedVersion version = issueDetailPresenter.
                                getVersionById(Long.valueOf(mDetailEntity.getNewValue()));

                        if (version != null) {
                            newValue = version.getName();
                        }
                    }

                    if (mDetailEntity.getOldValue() != null) {
                        FixedVersion version = issueDetailPresenter.
                                getVersionById(Long.valueOf(mDetailEntity.getOldValue()));

                        if (version != null) {
                            oldValue = version.getName();
                        }
                    }
                    break;
                }

                case "status_id": {
                    if (mDetailEntity.getNewValue() != null) {
                        Status status = issueDetailPresenter.
                                getStatusById(Long.valueOf(mDetailEntity.getNewValue()));

                        if (status != null) {
                            newValue = status.getName();
                        }
                    }

                    if (mDetailEntity.getOldValue() != null) {
                        Status status = issueDetailPresenter.
                                getStatusById(Long.valueOf(mDetailEntity.getOldValue()));

                        if (status != null) {
                            oldValue = status.getName();
                        }
                    }
                    break;
                }

                case "tracker_id": {
                    if (mDetailEntity.getNewValue() != null) {
                        Tracker tracker = issueDetailPresenter.
                                getTrackerById(Long.valueOf(mDetailEntity.getNewValue()));

                        if (tracker != null) {
                            newValue = tracker.getName();
                        }
                    }

                    if (mDetailEntity.getOldValue() != null) {
                        Tracker tracker = issueDetailPresenter.
                                getTrackerById(Long.valueOf(mDetailEntity.getOldValue()));

                        if (tracker != null) {
                            oldValue = tracker.getName();
                        }
                    }
                    break;
                }

                case "priority_id": {
                    if (mDetailEntity.getNewValue() != null) {
                        Priority priority = issueDetailPresenter.
                                getPriorityById(Long.valueOf(mDetailEntity.getNewValue()));

                        if (priority != null) {
                            newValue = priority.getName();
                        }
                    }

                    if (mDetailEntity.getOldValue() != null) {
                        Priority priority = issueDetailPresenter.
                                getPriorityById(Long.valueOf(mDetailEntity.getOldValue()));

                        if (priority != null) {
                            oldValue = priority.getName();
                        }
                    }
                    break;
                }

                case "subject":
                case "due_date":
                case "start_date":
                case "done_ratio":
                case "parent_id":
                case "category_id":
                case "estimated_hours":
                case "description": {
                    if (mDetailEntity.getNewValue() != null) {
                        newValue = mDetailEntity.getNewValue();
                    }

                    if (mDetailEntity.getOldValue() != null) {
                        oldValue = mDetailEntity.getOldValue();
                    }
                }
            }

        } else {
            if (mDetailEntity.getNewValue() != null) {
                newValue = mDetailEntity.getNewValue();
            }

            if (mDetailEntity.getOldValue() != null) {
                oldValue = mDetailEntity.getOldValue();
            }
        }


        if (mDetailEntity.getProperty().equalsIgnoreCase("attachment")) {
            prefixStr = "<b>File</b> ";

            string = string.concat(prefixStr);

            if (newValue != null && newValue.length() > 0) {
                string = string.concat(newValue + " <b>added</b>");

            } else {
                string = string.concat(oldValue + " <b>deleted</b>");
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

                case "description":
                    prefixStr = "<b>Description</b> ";
                    break;

                case "priority_id":
                    prefixStr = "<b>Priority</b> ";
                    break;

                case "category_id":
                    prefixStr = "<b>Category</b> ";
                    break;

                case "estimated_hours":
                    prefixStr = "<b>Estimated time </b> ";
                    break;

                case "due_date":
                    prefixStr = "<b>Due date </b> ";
                    break;

                case "start_date":
                    prefixStr = "<b>Start date </b> ";
                    break;

                case "done_ratio":
                    prefixStr = "<b>% Done </b> ";
                    break;

                default:
                    prefixStr = "";
            }

            string = string.concat(prefixStr);

            if (mDetailEntity.getName().equalsIgnoreCase("category_id")) {

                string = string.concat("changed");

            } else if (newValue != null && oldValue != null && newValue.length() > 0 && oldValue.length() > 0) {

                string = string.concat("changed from " + "<b>" + oldValue + "</b>" + " to " + "<b>" + newValue + "</b>");

            } else if (newValue != null && newValue.length() > 0) {

                string = string.concat("set to " + "<b>" + newValue + "</b>");

            } else if (oldValue != null && oldValue.length() > 0) {
                string = string.concat("deleted " + "<b>" + oldValue + "</b>");
            }
        }

        mDetailTextView.setText(Html.fromHtml(string));
    }
}