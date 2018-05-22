package com.igorkazakov.user.redminepro.screen.calendar;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.application.RedmineApplication;
import com.igorkazakov.user.redminepro.models.TimeModel;
import com.igorkazakov.user.redminepro.repository.OggyRepository;
import com.igorkazakov.user.redminepro.screen.base.BaseFragment;
import com.igorkazakov.user.redminepro.utils.ColorUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarFragment extends BaseFragment implements CalendarView {

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

    @InjectPresenter
    public CalendarPresenter mPresenter;

    @Inject
    OggyRepository mRepository;

    private int colorHoliday;
    private int colorHospital;
    private int colorVacation;

    @ProvidePresenter
    CalendarPresenter provideCalendarPresenter() {
        return new CalendarPresenter(mRepository);
    }

    @Override
    public MvpDelegate getMvpDelegate() {
        RedmineApplication.getComponent().inject(this);
        return super.getMvpDelegate();
    }

    public static CalendarFragment newInstance() {
        CalendarFragment calendarFragment = new CalendarFragment();
        return calendarFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.content_calendar, container, false);

        ButterKnife.bind(this, view);

        initCalendarView();
        initColors();

        return view;
    }

    private void initColors() {
        colorHoliday = ContextCompat.getColor(getActivity(),
                R.color.color_activity_calendar_holiday);
        colorHospital = ContextCompat.getColor(getActivity(),
                R.color.color_activity_calendar_hospital);
        colorVacation = ContextCompat.getColor(getActivity(),
                R.color.color_activity_calendar_vacation);
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
        mContentView.setCardBackgroundColor(ColorUtils.getColorForKpi(kpi, getActivity()));
    }

    private void initCalendarView() {

        mCalendarView.setOnDateChangedListener((widget, date, selected) -> {
            mPresenter.onDateClick(date.getDate());
        });
    }
}
