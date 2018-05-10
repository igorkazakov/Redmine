package com.igorkazakov.user.redminepro.repository;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.api.responseEntity.CalendarDay.OggyCalendarDay;
import com.igorkazakov.user.redminepro.database.realm.CalendarDayDAO;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by user on 14.07.17.
 */

public class OggyRepository {

    public static Observable<List<OggyCalendarDay>> getCalendarDays(int month, int year) {

        String login = PreferenceUtils.getInstance().getUserLogin();
        String password = PreferenceUtils.getInstance().getUserPassword();

        return ApiFactory.getOggyService()
                .getCalendarDays(login, password, month, year)
                .map(calendarDays -> {

                    CalendarDayDAO.saveCalendarDays(calendarDays);

                    return calendarDays;
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static Observable<List<OggyCalendarDay>> getCalendarDaysForYear() {

        int year = DateUtils.getCurrentYear();

        Observable[] observables = new Observable[]{
                OggyRepository.getCalendarDays(1, year),
                OggyRepository.getCalendarDays(2, year),
                OggyRepository.getCalendarDays(3, year),
                OggyRepository.getCalendarDays(4, year),
                OggyRepository.getCalendarDays(5, year),
                OggyRepository.getCalendarDays(6, year),
                OggyRepository.getCalendarDays(7, year),
                OggyRepository.getCalendarDays(8, year),
                OggyRepository.getCalendarDays(9, year),
                OggyRepository.getCalendarDays(10, year),
                OggyRepository.getCalendarDays(11, year),
                OggyRepository.getCalendarDays(12, year)};

        return Observable.zip(observables, args -> {

            List<OggyCalendarDay> list1 = new ArrayList<>();
            for (Object arg : args) {
                list1.addAll((List<OggyCalendarDay>) arg);
            }

            return list1;
        });
    }
}
