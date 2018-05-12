package com.igorkazakov.user.redminepro.repository;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.api.responseEntity.CalendarDay.OggyCalendarDay;
import com.igorkazakov.user.redminepro.database.realm.CalendarDayDAO;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


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

        List<Observable> observables = new ArrayList<>();

        observables.add(OggyRepository.getCalendarDays(1, year));
        observables.add(OggyRepository.getCalendarDays(2, year));
        observables.add(OggyRepository.getCalendarDays(3, year));
        observables.add(OggyRepository.getCalendarDays(4, year));
        observables.add(OggyRepository.getCalendarDays(5, year));
        observables.add(OggyRepository.getCalendarDays(6, year));
        observables.add(OggyRepository.getCalendarDays(7, year));
        observables.add(OggyRepository.getCalendarDays(8, year));
        observables.add(OggyRepository.getCalendarDays(9, year));
        observables.add(OggyRepository.getCalendarDays(10, year));
        observables.add(OggyRepository.getCalendarDays(11, year));
        observables.add(OggyRepository.getCalendarDays(12, year));

        Observable.zip(observables, new Function<Object[], List<OggyCalendarDay>>() {
            @Override
            public List<OggyCalendarDay> apply(Object[] objects) throws Exception {
                return null;
            }
        });

        return Observable.zip(observables, args -> {

            List<OggyCalendarDay> list1 = new ArrayList<>();
            for (Object arg : args) {
                list1.addAll((List<OggyCalendarDay>) arg);
            }

            return list1;
        });
    }
}
