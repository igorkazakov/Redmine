package com.igorkazakov.user.redminepro.screen.IssueDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.entity.JournalEntity;

import java.util.List;

/**
 * Created by user on 03.08.17.
 */

public class JournalAdapter extends RecyclerView.Adapter<JournalHolder> {

    private List<JournalEntity> journalEntities;
    private IssueDetailPresenter issueDetailPresenter;

    public JournalAdapter(List<JournalEntity> journalEntities, IssueDetailPresenter issueDetailPresenter) {
        this.journalEntities = journalEntities;
        this.issueDetailPresenter = issueDetailPresenter;
    }

    @Override
    public JournalHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.journal_issue_item, parent, false);
        return new JournalHolder(view, context);
    }

    @Override
    public void onBindViewHolder(JournalHolder holder, int position) {
        holder.bind(journalEntities.get(position), issueDetailPresenter);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return journalEntities.size();
    }
}