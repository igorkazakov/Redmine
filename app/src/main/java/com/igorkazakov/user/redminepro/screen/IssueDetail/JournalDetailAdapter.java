package com.igorkazakov.user.redminepro.screen.IssueDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.entity.DetailEntity;
import com.j256.ormlite.dao.ForeignCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 03.08.17.
 */

public class JournalDetailAdapter extends RecyclerView.Adapter<JournalDetailHolder> {

    private List<DetailEntity> detailEntities;

    public JournalDetailAdapter(ForeignCollection<DetailEntity> detailEntities) {
        this.detailEntities = new ArrayList<>(detailEntities);
    }

    @Override
    public JournalDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.detail_journal_item, parent, false);
        return new JournalDetailHolder(view, context);
    }

    @Override
    public void onBindViewHolder(JournalDetailHolder holder, int position) {
        holder.bind(detailEntities.get(position));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return detailEntities.size();
    }
}