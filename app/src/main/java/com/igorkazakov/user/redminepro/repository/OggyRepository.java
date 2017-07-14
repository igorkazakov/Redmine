package com.igorkazakov.user.redminepro.repository;

import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.entity.CalendarDayEntity;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;

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

                    List<CalendarDayEntity> calendarDayEntityList = CalendarDayEntity.convertItems(calendarDays);
                    DatabaseManager.getDatabaseHelper().getCalendarDayDAO().saveCalendarDays(calendarDayEntityList);

                    return Observable.just(calendarDayEntityList);
                })
                .onErrorResumeNext(throwable -> {

                    List<CalendarDayEntity> calendarDayEntities = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getAll();
                    return Observable.just(calendarDayEntities);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
