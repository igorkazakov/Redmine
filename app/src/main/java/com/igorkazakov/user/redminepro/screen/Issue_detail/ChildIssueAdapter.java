package com.igorkazakov.user.redminepro.screen.Issue_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.room.entity.IssueEntity;

import java.util.List;

/**
 * Created by user on 01.08.17.
 */

public class ChildIssueAdapter extends RecyclerView.Adapter<ChildIssueHolder> {

    private List<IssueEntity> issueModels;

    public ChildIssueAdapter(List<IssueEntity> issueModels) {
        this.issueModels = issueModels;
    }

    @Override
    public ChildIssueHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.child_issue_item, parent, false);
        return new ChildIssueHolder(view, context);
    }

    @Override
    public void onBindViewHolder(ChildIssueHolder holder, int position) {
        holder.bind(issueModels.get(position));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return issueModels.size();
    }
}
