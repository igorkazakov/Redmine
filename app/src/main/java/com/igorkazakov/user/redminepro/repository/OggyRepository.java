package com.igorkazakov.user.redminepro.repository;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.entity.CalendarDayEntity;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;

import java.util.Calendar;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.functions.Func6;
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
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static Observable<List<CalendarDayEntity>> getCalendarDaysForYear() {

        int year = Calendar.getInstance().get(Calendar.YEAR);
        Observable<List<CalendarDayEntity>> calendarDay1 = OggyRepository.getCalendarDays(1, year);
        Observable<List<CalendarDayEntity>> calendarDay2 = OggyRepository.getCalendarDays(2, year);
        Observable<List<CalendarDayEntity>> calendarDay3 = OggyRepository.getCalendarDays(3, year);
        Observable<List<CalendarDayEntity>> calendarDay4 = OggyRepository.getCalendarDays(4, year);
        Observable<List<CalendarDayEntity>> calendarDay5 = OggyRepository.getCalendarDays(5, year);
        Observable<List<CalendarDayEntity>> calendarDay6 = OggyRepository.getCalendarDays(6, year);
        Observable<List<CalendarDayEntity>> calendarDay7 = OggyRepository.getCalendarDays(7, year);
        Observable<List<CalendarDayEntity>> calendarDay8 = OggyRepository.getCalendarDays(8, year);
        Observable<List<CalendarDayEntity>> calendarDay9 = OggyRepository.getCalendarDays(9, year);
        Observable<List<CalendarDayEntity>> calendarDay10 = OggyRepository.getCalendarDays(10, year);
        Observable<List<CalendarDayEntity>> calendarDay11 = OggyRepository.getCalendarDays(11, year);
        Observable<List<CalendarDayEntity>> calendarDay12 = OggyRepository.getCalendarDays(12, year);


        Observable<List<CalendarDayEntity>> observable1 = Observable.zip(calendarDay1,
                calendarDay2,
                calendarDay3,
                calendarDay4,
                calendarDay5,
                calendarDay6,

                new Func6<List<CalendarDayEntity>,
                        List<CalendarDayEntity>,
                        List<CalendarDayEntity>,
                        List<CalendarDayEntity>,
                        List<CalendarDayEntity>,
                        List<CalendarDayEntity>,
                        List<CalendarDayEntity>>() {

                    @Override
                    public List<CalendarDayEntity> call(List<CalendarDayEntity> calendarDayEntities,
                                                        List<CalendarDayEntity> calendarDayEntities1,
                                                        List<CalendarDayEntity> calendarDayEntities2,
                                                        List<CalendarDayEntity> calendarDayEntities3,
                                                        List<CalendarDayEntity> calendarDayEntities4,
                                                        List<CalendarDayEntity> calendarDayEntities5) {
                        return null;
                    }
                });

        Observable<List<CalendarDayEntity>> observable2 = Observable.zip(calendarDay7,
                calendarDay8,
                calendarDay9,
                calendarDay10,
                calendarDay11,
                calendarDay12,

                new Func6<List<CalendarDayEntity>,
                        List<CalendarDayEntity>,
                        List<CalendarDayEntity>,
                        List<CalendarDayEntity>,
                        List<CalendarDayEntity>,
                        List<CalendarDayEntity>,
                        List<CalendarDayEntity>>() {

                    @Override
                    public List<CalendarDayEntity> call(List<CalendarDayEntity> calendarDayEntities,
                                                        List<CalendarDayEntity> calendarDayEntities1,
                                                        List<CalendarDayEntity> calendarDayEntities2,
                                                        List<CalendarDayEntity> calendarDayEntities3,
                                                        List<CalendarDayEntity> calendarDayEntities4,
                                                        List<CalendarDayEntity> calendarDayEntities5) {
                        return null;
                    }
                });

        return Observable.zip(observable1, observable2,
        new Func2<List<CalendarDayEntity>, List<CalendarDayEntity>, List<CalendarDayEntity>>() {
            @Override
            public List<CalendarDayEntity> call(List<CalendarDayEntity> listObservable, List<CalendarDayEntity> listObservable2) {
                return null;
            }
        });
    }
}
