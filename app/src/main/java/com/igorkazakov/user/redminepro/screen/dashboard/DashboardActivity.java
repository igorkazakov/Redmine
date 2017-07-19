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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.screen.general.LoadingDialog;
import com.igorkazakov.user.redminepro.screen.general.LoadingView;
import com.igorkazakov.user.redminepro.utils.TimeModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnChartValueSelectedListener, DashboardView {

    @BindView(R.id.chartWorkTime)
    PieChart mChartWorkTime;

    private DashboardPresenter mPresenter;
    private LoadingView mLoadingView;

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

    public void setupView() {
        setupChart(mPresenter.getHoursForYear(), mPresenter.calculateKpiForYear());
    }

    private int getColorForKpi(float kpi) {

        if (kpi >= mPresenter.getNormalKpi()) {
            return ContextCompat.getColor(this, R.color.colorRegular);

        } else {
            return ContextCompat.getColor(this, R.color.colorFuckup);
        }
    }

    private void setupChart(TimeModel model, float kpi) {
        mChartWorkTime.setUsePercentValues(true);
        mChartWorkTime.getDescription().setEnabled(false);
        mChartWorkTime.setCenterTextSize(16);
        mChartWorkTime.setCenterText("KPI\n" + String.valueOf(kpi));
        mChartWorkTime.setCenterTextColor(getColorForKpi(kpi));
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
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
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

        if (e == null)
            return;

        PieEntry entry = (PieEntry) e;
        mChartWorkTime.setCenterText("KPI\n" +
                String.valueOf(mPresenter.calculateKpiForYear()) + "\n" +
                entry.getLabel() + "\n" +
                String.valueOf((int)entry.getValue()) + "h");
    }

    @Override
    public void onNothingSelected() {}

    private void setData(TimeModel model) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        entries.add(new PieEntry(model.getRegularTime(), "REGULAR", null));
        entries.add(new PieEntry(model.getFuckupTime(), "F%CKUP", null));
        entries.add(new PieEntry(model.getTeamFuckupTime(), "TEAM F%UCKUP", null));

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
        data.setValueFormatter(new PercentFormatter());
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
