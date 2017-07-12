package com.igorkazakov.user.redminepro.screen.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.igorkazakov.user.redminepro.screen.general.LoadingView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnChartValueSelectedListener {

    @BindView(R.id.chartWorkTime)
    PieChart mCartWorkTime;

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

        setupChart();
    }

    private void setupChart() {
        mCartWorkTime.setUsePercentValues(true);
        mCartWorkTime.getDescription().setEnabled(false);

       // mCartWorkTime.setCenterTextTypeface(mTfLight);
        mCartWorkTime.setCenterText("rty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty qwerty ");

        mCartWorkTime.setDrawHoleEnabled(true);
        mCartWorkTime.setHoleColor(Color.WHITE);

        mCartWorkTime.setTransparentCircleColor(Color.WHITE);
        mCartWorkTime.setTransparentCircleAlpha(70);
        mCartWorkTime.setHoleRadius(65f);
        mCartWorkTime.setTransparentCircleRadius(70f);
        mCartWorkTime.setDrawCenterText(true);
        mCartWorkTime.setRotationAngle(0);
        mCartWorkTime.setRotationEnabled(false);
        mCartWorkTime.setHighlightPerTapEnabled(true);

        mCartWorkTime.setOnChartValueSelectedListener(this);

        mCartWorkTime.getLegend().setForm(Legend.LegendForm.CIRCLE);
        mCartWorkTime.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        mCartWorkTime.getLegend().setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        mCartWorkTime.getLegend().setTextSize(13);

        setData(3, 100);

        mCartWorkTime.setEntryLabelColor(Color.WHITE);
        mCartWorkTime.setEntryLabelTextSize(12f);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        mCartWorkTime.setCenterText("VAL SELECTED"+
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        entries.add(new PieEntry(60,
                "Regular",
                null));

        entries.add(new PieEntry(30,
                "F%ckup",
                null));

        entries.add(new PieEntry(10,
                "Team f%ckup",
                null));


        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(10f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        //for (int c : Color.BLUE)
            colors.add(Color.BLUE);

        //for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(Color.RED);

        //for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(Color.GREEN);


        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        //data.setValueTypeface(mTfLight);
        mCartWorkTime.setData(data);

        // undo all highlights
        mCartWorkTime.highlightValues(null);

        mCartWorkTime.invalidate();
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
}
