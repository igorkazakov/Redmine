package com.igorkazakov.user.redminepro.screen.calendar_screen;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.entity.CalendarDayEntity;
import com.igorkazakov.user.redminepro.models.TimeModel;
import com.igorkazakov.user.redminepro.repository.OggyRepository;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.igorkazakov.user.redminepro.utils.KPIUtils;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;
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

        if (PreferenceUtils.getInstance().getCalendarDownloaded()) {

            OggyRepository.getCalendarDays(DateUtils.getCurrentMonth(), DateUtils.getCurrentYear())
                    .doOnSubscribe(mView::showLoading)
                    .compose(mLifecycleHandler.reload(R.id.calendar_month_days_request))
                    .subscribe(response -> {
                                List<CalendarDayEntity> calendarDayEntities = DatabaseManager.
                                        getDatabaseHelper().getCalendarDayDAO().getAll();
                                createMonthIndicatorArrays(calendarDayEntities);
                            },
                            Throwable::printStackTrace);
        } else {

            OggyRepository.getCalendarDaysForYear()
                    .doOnSubscribe(mView::showLoading)
                    .compose(mLifecycleHandler.reload(R.id.calendar_month_days_request))
                    .subscribe(response -> {
                                createMonthIndicatorArrays(response);
                                PreferenceUtils.getInstance().saveCalendarDownloaded(true);
                            },
                            Throwable::printStackTrace);
        }
    }

    public void createMonthIndicatorArrays(List<CalendarDayEntity> response) {

        for (CalendarDayEntity day: response) {

            Date dayDate = DateUtils.dateFromString(day.getDate(),
                    DateUtils.getSimpleFormatter());

            switch (day.getType()) {
                case CalendarDayEntity.FEAST:
                case CalendarDayEntity.HOLIDAY:
                    listOfHoliday.add(CalendarDay.from(dayDate));
                    break;

                case CalendarDayEntity.HOSPITAL:
                    listOfHospital.add(CalendarDay.from(dayDate));
                    break;

                case CalendarDayEntity.VACATION:
                    listOfVacation.add(CalendarDay.from(dayDate));
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
