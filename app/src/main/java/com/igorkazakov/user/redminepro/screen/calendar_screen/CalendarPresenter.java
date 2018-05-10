package com.igorkazakov.user.redminepro.screen.calendar_screen;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.api.responseEntity.CalendarDay.OggyCalendarDay;
import com.igorkazakov.user.redminepro.models.TimeModel;
import com.igorkazakov.user.redminepro.repository.OggyRepository;
import com.igorkazakov.user.redminepro.utils.KPIUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.arturvasilov.rxloader.LifecycleHandler;

/**
 * Created by Igor on 29.08.2017.
 */

public class CalendarPresenter {

    private LifecycleHandler mLifecycleHandler;
    private CalendarView mView;

    private ArrayList<CalendarDay> listOfHoliday = new ArrayList<>();
    private ArrayList<CalendarDay> listOfHospital = new ArrayList<>();
    private ArrayList<CalendarDay> listOfVacation = new ArrayList<>();

    public CalendarPresenter(@NonNull LifecycleHandler lifecycleHandler, CalendarView view) {
        mLifecycleHandler = lifecycleHandler;
        mView = view;
    }

    public void loadAllCalendarDays() {

        OggyRepository.getCalendarDaysForYear()
                .doOnSubscribe(mView::showLoading)
                .doOnTerminate(mView::hideLoading)
                .compose(mLifecycleHandler.reload(R.id.calendar_month_days_request))
                .subscribe(response -> createMonthIndicatorArrays(response),
                        Throwable::printStackTrace);

    }

    public void createMonthIndicatorArrays(List<OggyCalendarDay> response) {

        for (OggyCalendarDay day : response) {

            switch (day.getType()) {
                case OggyCalendarDay.FEAST:
                case OggyCalendarDay.HOLIDAY:
                    listOfHoliday.add(CalendarDay.from(day.getDate()));
                    break;

                case OggyCalendarDay.HOSPITAL:
                    listOfHospital.add(CalendarDay.from(day.getDate()));
                    break;

                case OggyCalendarDay.VACATION:
                    listOfVacation.add(CalendarDay.from(day.getDate()));
                    break;
            }
        }

        mView.showMonthIndicators(listOfHoliday, listOfHospital, listOfVacation);
        mView.showCurrentDay();
        mView.hideLoading();
    }

    public void onDateClick(Date day) {

        TimeModel model = KPIUtils.getHoursForDate(day);
        float kpi = KPIUtils.calculateKpiForDate(day);
        mView.showDayWorkHours(kpi, model);
    }
}
