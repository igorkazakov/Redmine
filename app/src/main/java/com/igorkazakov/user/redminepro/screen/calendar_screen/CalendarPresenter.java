package com.igorkazakov.user.redminepro.screen.calendar_screen;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.entity.CalendarDayEntity;
import com.igorkazakov.user.redminepro.repository.OggyRepository;
import com.igorkazakov.user.redminepro.utils.DateUtils;
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
                .subscribe(response -> fetchCalendarDays(DateUtils.getCurrentMonth()),
                        Throwable::printStackTrace);
    }

    public void fetchCalendarDays(int month) {

        AsyncTask fetch = new FetchCalendarDaysTask();
        fetch.execute(new Integer[]{month});
    }

    private class FetchCalendarDaysTask extends AsyncTask<Integer, Void, Boolean> {

        protected Boolean doInBackground(Integer... month) {

            mView.showLoading();
            List<CalendarDayEntity> calendarDayEntityList = DatabaseManager.getDatabaseHelper()
                    .getCalendarDayDAO()
                    .getCalendarMonthDaysWithDate(DateUtils.getMonthInterval(month[0]));

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

            return true;
        }

        protected void onPostExecute(Boolean arg) {

            mView.showMonthIndicators(listOfHoliday, listOfHospital, listOfVacation);
            mView.hideLoading();
        }
    }
}
