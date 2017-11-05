package com.igorkazakov.user.redminepro.screen.calendar_screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.models.TimeModel;
import com.igorkazakov.user.redminepro.screen.general.LoadingFragment;
import com.igorkazakov.user.redminepro.screen.general.LoadingView;
import com.igorkazakov.user.redminepro.utils.ColorUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class CalendarActivity extends AppCompatActivity implements CalendarView {

    @BindView(R.id.calendarView)
    MaterialCalendarView mCalendarView;

    @BindView(R.id.layout_container)
    FrameLayout mLayoutContainer;

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

    private CalendarPresenter mPresenter;
    private LoadingView mLoadingView;

    private int colorHoliday;
    private int colorHospital;
    private int colorVacation;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, CalendarActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler
                .create(this, getSupportLoaderManager());
        mPresenter = new CalendarPresenter(lifecycleHandler, this);
        mLoadingView = new LoadingFragment(this, mLayoutContainer);
        initCalendarView();
        initColors();
        mPresenter.loadAllCalendarDays();
    }

    private void initColors() {
        colorHoliday = ContextCompat.getColor(CalendarActivity.this,
                R.color.color_activity_calendar_holiday);
        colorHospital = ContextCompat.getColor(CalendarActivity.this,
                R.color.color_activity_calendar_hospital);
        colorVacation = ContextCompat.getColor(CalendarActivity.this,
                R.color.color_activity_calendar_vacation);
    }

    @Override
    public void showLoading() {
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hideLoading();
    }

    @Override
    public void showMonthIndicators(ArrayList<CalendarDay> listOfHoliday,
                                    ArrayList<CalendarDay> listOfHospital,
                                    ArrayList<CalendarDay> listOfVacation) {

        mCalendarView.removeDecorators();
        mCalendarView.addDecorator(new EventDecorator(colorHoliday, listOfHoliday));
        mCalendarView.addDecorator(new EventDecorator(colorHospital, listOfHospital));
        mCalendarView.addDecorator(new EventDecorator(colorVacation, listOfVacation));
    }

    @Override
    public void showCurrentDay() {
        Date currentDate = Calendar.getInstance().getTime();
        mPresenter.onDateClick(currentDate);
        mCalendarView.setSelectedDate(currentDate);
    }

    @Override
    public void showDayWorkHours(float kpi, TimeModel model) {

        mRowTitle.setText(getResources().getText(R.string.title_kpi_statistics_calendar));
        mRegularTextView.setText(String.valueOf(model.getRegularTime()).replace(".0", ""));
        mFuckupTextView.setText(String.valueOf(model.getFuckupTime()).replace(".0", ""));
        mTeamFuckupTextView.setText(String.valueOf(model.getTeamFuckupTime()).replace(".0", ""));
        mContentView.setCardBackgroundColor(ColorUtils.getColorForKpi(kpi, this));
    }

    private void initCalendarView() {

        mCalendarView.setOnDateChangedListener((widget, date, selected) -> {
            mPresenter.onDateClick(date.getDate());
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}