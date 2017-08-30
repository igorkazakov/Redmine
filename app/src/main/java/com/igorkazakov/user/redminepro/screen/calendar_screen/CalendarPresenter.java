package com.igorkazakov.user.redminepro.screen.calendar_screen;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.entity.CalendarDayEntity;
import com.igorkazakov.user.redminepro.repository.OggyRepository;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import ru.arturvasilov.rxloader.LifecycleHandler;

/**
 * Created by Igor on 29.08.2017.
 */

public class CalendarPresenter {

    private LifecycleHandler mLifecycleHandler;
    private CalendarView mView;

    public CalendarPresenter(@NonNull LifecycleHandler lifecycleHandler, CalendarView view) {
        mLifecycleHandler = lifecycleHandler;
        mView = view;
    }

    public void loadAllCalendarDays() {

        OggyRepository.getCalendarDaysForYear()
                .doOnSubscribe(mView::showLoading)
                .doOnTerminate(mView::hideLoading)
                .compose(mLifecycleHandler.reload(R.id.calendar_month_days_request))
                .subscribe(response -> fetchCalendarDays(DateUtils.getCurrentMonth()),
                        Throwable::printStackTrace);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void fetchCalendarDays(int month) {

        ArrayList<CalendarDay> listOfHoliday = new ArrayList<>();
        ArrayList<CalendarDay> listOfHospital = new ArrayList<>();
        ArrayList<CalendarDay> listOfVacation = new ArrayList<>();

        CompletableFuture.supplyAsync(() -> {
            List<CalendarDayEntity> calendarDayEntityList = DatabaseManager.getDatabaseHelper()
                    .getCalendarDayDAO()
                    .getCalendarMonthDaysWithDate(DateUtils.getMonthInterval(month));


            for (CalendarDayEntity day: calendarDayEntityList) {

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
        }).thenApply(() -> {
            mView.showMonthIndicators(listOfHoliday, listOfHospital, listOfVacation);
        });

    }
}
