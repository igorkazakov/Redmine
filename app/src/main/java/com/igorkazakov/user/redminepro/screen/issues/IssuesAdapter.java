package com.igorkazakov.user.redminepro.screen.issues;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.models.IssueModel;

import java.util.List;

/**
 * Created by user on 25.07.17.
 */

public class IssuesAdapter extends RecyclerView.Adapter<IssueHolder> {

    private List<IssueModel> issueModels;

    public IssuesAdapter(List<IssueModel> issueModels) {
        this.issueModels = issueModels;
    }

    @Override
    public IssueHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.issue_item, parent, false);
        return new IssueHolder(view, context);
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
