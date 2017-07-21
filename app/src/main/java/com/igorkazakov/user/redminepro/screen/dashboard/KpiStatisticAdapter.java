package com.igorkazakov.user.redminepro.screen.dashboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.models.StatisticModel;

import java.util.List;

/**
 * Created by user on 20.07.17.
 */

public class KpiStatisticAdapter extends RecyclerView.Adapter<StatisticHolder> {

    private List<StatisticModel> items;

    public KpiStatisticAdapter(List<StatisticModel> items) {
        this.items = items;
    }

    @Override
    public StatisticHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.kpi_statistic_item, parent, false);
        return new StatisticHolder(view, context);
    }

    @Override
    public void onBindViewHolder(StatisticHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
