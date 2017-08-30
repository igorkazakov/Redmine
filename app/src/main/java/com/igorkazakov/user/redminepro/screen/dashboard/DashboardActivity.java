package com.igorkazakov.user.redminepro.screen.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.igorkazakov.user.redminepro.BuildConfig;
import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.models.StatisticModel;
import com.igorkazakov.user.redminepro.models.TimeModel;
import com.igorkazakov.user.redminepro.screen.calendar_screen.CalendarActivity;
import com.igorkazakov.user.redminepro.screen.general.LoadingFragment;
import com.igorkazakov.user.redminepro.screen.general.LoadingView;
import com.igorkazakov.user.redminepro.screen.issues.IssuesActivity;
import com.igorkazakov.user.redminepro.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnChartValueSelectedListener, DashboardView {

    @BindView(R.id.chartWorkTime)
    PieChart mChartWorkTime;


    @BindView(R.id.statisticRecyclerView)
    RecyclerView mStatisticRecyclerView;

    @BindView(R.id.remainHoursLabel)
    TextView mRemainHoursLabel;

    @BindView(R.id.remainKpiLabel)
    TextView mRemainKpiLabel;

    @BindView(R.id.dashboardContainer)
    FrameLayout mContentView;


    private DashboardPresenter mPresenter;
    private LoadingView mLoadingView;
    private KpiStatisticAdapter mAdapter;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, DashboardActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mLoadingView = new LoadingFragment(this, mContentView);

        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new DashboardPresenter(lifecycleHandler, this);
        mPresenter.loadRedmineData();
        mPresenter.tryLoadDashboardData();
    }

    @Override
    public void setupCurrentWeekStatistic(float remainHours, float remainDays, float weekHours) {

        String text = String.format(getResources().getString(R.string.remain_kpi),
                String.valueOf(remainHours),
                String.valueOf(BuildConfig.NORMAL_KPI)).replace(".0", "");
        mRemainKpiLabel.setText(Html.fromHtml(text));

        text = String.format(getResources().getString(R.string.remain_hours),
                String.valueOf(remainDays),
                String.valueOf(weekHours)).replace(".0", "");
        mRemainHoursLabel.setText(Html.fromHtml(text));
    }

    @Override
    public void setupStatisticRecyclerView(List<StatisticModel> timeModelList) {

        mAdapter = new KpiStatisticAdapter(timeModelList);
        mStatisticRecyclerView.setLayoutManager(new LinearLayoutManager(this){
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
        mChartWorkTime.setCenterTextColor(ColorUtils.getColorForKpi(kpi, this));
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
        mChartWorkTime.setOnChartValueSelectedListener(this);
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

    @Override
    public void onValueSelected(Entry e, Highlight h) {

//        if (e == null)
//            return;
//
//        PieEntry entry = (PieEntry) e;
//        mChartWorkTime.setCenterText("KPI\n" +
//                String.valueOf(mPresenter.calculateKpiForYear()) + "\n" +
//                entry.getLabel() + "\n" +
//                String.valueOf((int)entry.getValue()) + "h");
    }

    @Override
    public void onNothingSelected() {}

    private void setData(TimeModel model) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        entries.add(new PieEntry(model.getRegularTime(), "REGULAR", null));
        entries.add(new PieEntry(model.getFuckupTime(), "F%CKUP", null));
        entries.add(new PieEntry(model.getTeamFuckupTime(), "DUTIES", null));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(10f);

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(ContextCompat.getColor(this, R.color.colorRegular));
        colors.add(ContextCompat.getColor(this, R.color.colorFuckup));
        colors.add(ContextCompat.getColor(this, R.color.colorTeamFuckup));
        dataSet.setColors(colors);
        dataSet.setSelectionShift(10f);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);
        mChartWorkTime.setData(data);
        mChartWorkTime.invalidate();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_issues) {
            IssuesActivity.start(this);

        } else if (id == R.id.nav_calendar) {
            CalendarActivity.start(this);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showLoading() {
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hideLoading();
    }


}
