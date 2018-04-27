package com.igorkazakov.user.redminepro.repository;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.entity.CalendarDayEntity;
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

    public static Observable<List<CalendarDayEntity>> getCalendarDays(int month, int year) {

        String login = PreferenceUtils.getInstance().getUserLogin();
        String password = PreferenceUtils.getInstance().getUserPassword();

        return ApiFactory.getOggyService()
                .getCalendarDays(login, password, month, year)
                .flatMap(calendarDays -> {

                    List<CalendarDayEntity> calendarDayEntityList = CalendarDayEntity
                            .convertItems(calendarDays);

                    DatabaseManager.getDatabaseHelper()
                            .getCalendarDayDAO()
                            .saveCalendarDays(calendarDayEntityList);

                    return Observable.just(calendarDayEntityList);
                })
                .onErrorResumeNext(throwable -> {

                    List<CalendarDayEntity> calendarDayEntities = DatabaseManager.getDatabaseHelper()
                            .getCalendarDayDAO()
                            .getAll();

                    return Observable.just(calendarDayEntities);
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static Observable<List<CalendarDayEntity>> getCalendarDaysForYear() {

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

            List<CalendarDayEntity> list1 = new ArrayList<>();
            for (Object arg : args) {
                list1.addAll((List<CalendarDayEntity>) arg);
            }
            return list1;
        });
    }
}
