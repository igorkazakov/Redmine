package com.igorkazakov.user.redminepro.screen.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import com.igorkazakov.user.redminepro.screen.general.LoadingDialog;
import com.igorkazakov.user.redminepro.screen.general.LoadingView;
import com.igorkazakov.user.redminepro.utils.ColorUtils;
import com.igorkazakov.user.redminepro.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mLoadingView = LoadingDialog.view(getSupportFragmentManager());

        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new DashboardPresenter(lifecycleHandler, this);
        mPresenter.tryLoadDashboardData();


    }

    private void setupStatisticRecyclerView() {

        List<StatisticModel> timeModelList = new ArrayList<>();
        timeModelList.add(new StatisticModel(mPresenter.getHoursForCurrentMonth(), mPresenter.calculateKpiForCurrentMonth(), "Current month"));
        timeModelList.add(new StatisticModel(mPresenter.getHoursForPreviousWeek(), mPresenter.calculateKpiForPreviousWeek(), "Previous week"));
        timeModelList.add(new StatisticModel(mPresenter.getHoursForCurrentWeek(), mPresenter.calculateKpiForCurrentWeek(), "Current week"));
        timeModelList.add(new StatisticModel(mPresenter.getHoursForYesterday(), mPresenter.calculateKpiForDate(DateUtils.getYesterday()), "Yesterday"));
        timeModelList.add(new StatisticModel(mPresenter.getHoursForToday(), mPresenter.calculateKpiForDate(new Date()), "Today"));

        mAdapter = new KpiStatisticAdapter(timeModelList);
        mStatisticRecyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mStatisticRecyclerView.setAdapter(mAdapter);
    }

    public void setupView() {
        setupChart(mPresenter.getHoursForYear(), mPresenter.calculateKpiForYear());
        setupStatisticRecyclerView();
        String text = String.format(getResources().getString(R.string.remain_kpi),
                String.valueOf(mPresenter.remainHoursForNormalKpi()),
                String.valueOf(BuildConfig.NORMAL_KPI));
        mRemainKpiLabel.setText(Html.fromHtml(text));

        text = String.format(getResources().getString(R.string.remain_hours),
                String.valueOf(mPresenter.remainDaysForWeek()),
                String.valueOf(mPresenter.getWholeCurrentWeekHoursNorm()));
        mRemainHoursLabel.setText(Html.fromHtml(text));
    }

    private void setupChart(TimeModel model, float kpi) {

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
        Legend l = mChartWorkTime.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setTextSize(9);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

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
        entries.add(new PieEntry(model.getTeamFuckupTime(), "TEAM F%CKUP", null));

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

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
