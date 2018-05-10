package com.igorkazakov.user.redminepro.screen.issues;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;

import java.util.List;

/**
 * Created by user on 25.07.17.
 */

public class IssuesAdapter extends RecyclerView.Adapter<IssueHolder> {

    private List<Issue> issueModels;

    public IssuesAdapter(List<Issue> issueModels) {
        this.issueModels = issueModels;
    }

    @Override
    public IssueHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.issue_item, parent, false);
        IssueHolder holder = new IssueHolder(view, context);
        return holder;
    }

    @Override
    public void onBindViewHolder(IssueHolder holder, int position) {
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
