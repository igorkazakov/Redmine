package com.igorkazakov.user.redminepro.screen.dashboard;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.igorkazakov.user.redminepro.BuildConfig;
import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.application.RedmineApplication;
import com.igorkazakov.user.redminepro.models.StatisticModel;
import com.igorkazakov.user.redminepro.models.TimeModel;
import com.igorkazakov.user.redminepro.repository.Repository;
import com.igorkazakov.user.redminepro.screen.base.BaseFragment;
import com.igorkazakov.user.redminepro.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardFragment extends BaseFragment
        implements DashboardView {

    @BindView(R.id.chartWorkTime)
    PieChart mChartWorkTime;

    @BindView(R.id.scrollView)
    ScrollView mScrollView;

    @BindView(R.id.statisticRecyclerView)
    RecyclerView mStatisticRecyclerView;

    @BindView(R.id.remainHoursLabel)
    TextView mRemainHoursLabel;

    @BindView(R.id.remainKpiLabel)
    TextView mRemainKpiLabel;

    @BindView(R.id.dashboardContainer)
    FrameLayout mContentView;

    @Inject
    Repository mRepository;

    @InjectPresenter
    public DashboardPresenter mPresenter;
    private KpiStatisticAdapter mAdapter;

    @ProvidePresenter
    DashboardPresenter provideDashboardPresenter() {
        return new DashboardPresenter(mRepository);
    }

    @Override
    public MvpDelegate getMvpDelegate() {
        RedmineApplication.getComponent().inject(this);
        return super.getMvpDelegate();
    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.content_dashboard, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mScrollView != null) {
            mScrollView.smoothScrollTo(0, 0);
        }
    }

    @Override
    public void setupCurrentWeekStatistic(float remainHoursForKpi, float remainHoursForWeek, float weekHours) {

        String text;
        if (remainHoursForKpi > 0) {
            text = String.format(getResources().getString(R.string.remain_kpi),
                    String.valueOf(remainHoursForKpi),
                    String.valueOf(BuildConfig.NORMAL_KPI)).replace(".0", "");
            mRemainKpiLabel.setText(Html.fromHtml(text));

        } else {
            mRemainKpiLabel.setText(
                    Html.fromHtml(getResources().getString(R.string.weekly_kpi_complete)));
        }

        if (remainHoursForWeek > 0) {
            text = String.format(getResources().getString(R.string.remain_hours),
                    String.valueOf(remainHoursForWeek),
                    String.valueOf(weekHours)).replace(".0", "");
            mRemainHoursLabel.setText(Html.fromHtml(text));

        } else {
            mRemainHoursLabel.setText(
                    Html.fromHtml(getResources().getString(R.string.weekly_clock_complete)));
        }
    }

    @Override
    public void setupStatisticRecyclerView(List<StatisticModel> timeModelList) {

        mAdapter = new KpiStatisticAdapter(timeModelList);
        mStatisticRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mStatisticRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setupChart(TimeModel model, float kpi) {

        mChartWorkTime.getDescription().setEnabled(false);
        mChartWorkTime.setCenterTextSize(16);
        mChartWorkTime.setCenterText("KPI\n" + String.valueOf(kpi));
        mChartWorkTime.setCenterTextColor(ColorUtils.getColorForKpi(kpi, getActivity()));
        mChartWorkTime.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mChartWorkTime.setDrawHoleEnabled(true);
        mChartWorkTime.setHoleColor(Color.WHITE);
        mChartWorkTime.setTransparentCircleColor(Color.WHITE);
        mChartWorkTime.setTransparentCircleAlpha(70);
        mChartWorkTime.setHoleRadius(65f);
        mChartWorkTime.setTransparentCircleRadius(70f);
        mChartWorkTime.setDrawCenterText(true);
        mChartWorkTime.setRotationAngle(0);
        mChartWorkTime.setRotationEnabled(false);
        mChartWorkTime.setHighlightPerTapEnabled(true);

        Legend legend = mChartWorkTime.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setTextSize(9);
        legend.setDrawInside(false);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);

        setData(model);
        mChartWorkTime.setEntryLabelColor(Color.WHITE);
        mChartWorkTime.setDrawEntryLabels(false);
        mChartWorkTime.setEntryLabelTextSize(13f);
    }

    private void setData(TimeModel model) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        entries.add(new PieEntry(model.getRegularTime(), "REGULAR", null));
        entries.add(new PieEntry(model.getFuckupTime(), "F%CKUP", null));
        entries.add(new PieEntry(model.getTeamFuckupTime(), "DUTIES", null));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(10f);

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(ContextCompat.getColor(getActivity(), R.color.colorRegular));
        colors.add(ContextCompat.getColor(getActivity(), R.color.colorFuckup));
        colors.add(ContextCompat.getColor(getActivity(), R.color.colorTeamFuckup));
        dataSet.setColors(colors);
        dataSet.setSelectionShift(10f);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);
        mChartWorkTime.setData(data);
        mChartWorkTime.invalidate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            mPresenter.tryLoadDashboardData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.dashboard, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
