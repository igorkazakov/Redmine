package com.igorkazakov.user.redminepro.screen.dashboard;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.models.StatisticModel;
import com.igorkazakov.user.redminepro.utils.ColorUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 20.07.17.
 */

public class StatisticHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.regularHours)
    TextView mRegularTextView;

    @BindView(R.id.fuckupHours)
    TextView mFuckupTextView;

    @BindView(R.id.teamFuckupHours)
    TextView mTeamFuckupTextView;

    @BindView(R.id.rowTitle)
    TextView mRowTitle;

    @BindView(R.id.contentView)
    CardView mContentView;

    Context mContext;

    public StatisticHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    public void bind(StatisticModel model) {

        mRowTitle.setText(model.getTitle());
        mRegularTextView.setText(String.valueOf(model.getTimeModel().getRegularTime()));
        mFuckupTextView.setText(String.valueOf(model.getTimeModel().getFuckupTime()));
        mTeamFuckupTextView.setText(String.valueOf(model.getTimeModel().getTeamFuckupTime()));
        mContentView.setCardBackgroundColor(ColorUtils.getColorForKpi(model.getKpi(), mContext));
    }
}
