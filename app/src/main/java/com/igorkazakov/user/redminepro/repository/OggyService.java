//package com.igorkazakov.user.redminepro.repository;
//
//import android.support.annotation.NonNull;
//
//import com.igorkazakov.user.redminepro.api.ApiFactory;
//import com.igorkazakov.user.redminepro.api.responseEntity.CalendarDay.OggyCalendarDay;
//import com.igorkazakov.user.redminepro.application.RedmineApplication;
//import com.igorkazakov.user.redminepro.database.realm.CalendarDayDAO;
//import com.igorkazakov.user.redminepro.utils.DateUtils;
//import com.igorkazakov.user.redminepro.utils.PreferenceUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.inject.Inject;
//
//import io.reactivex.Single;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.schedulers.Schedulers;
//
//
///**
// * Created by user on 14.07.17.
// */
//
//public class OggyService {
//
//    @Inject
//    PreferenceUtils mPreferenceUtils;
//
//    public OggyService() {
//        RedmineApplication.getComponent().inject(this);
//    }
//
//    @NonNull
//    public Single<List<OggyCalendarDay>> getCalendarDaysForYear() {
//
//        int year = DateUtils.getCurrentYear();
//
//        List<Single<List<OggyCalendarDay>>> observables = new ArrayList<>();
//        observables.add(getCalendarDays(1, year));
//        observables.add(getCalendarDays(2, year));
//        observables.add(getCalendarDays(3, year));
//        observables.add(getCalendarDays(4, year));
//        observables.add(getCalendarDays(5, year));
//        observables.add(getCalendarDays(6, year));
//        observables.add(getCalendarDays(7, year));
//        observables.add(getCalendarDays(8, year));
//        observables.add(getCalendarDays(9, year));
//        observables.add(getCalendarDays(10, year));
//        observables.add(getCalendarDays(11, year));
//        observables.add(getCalendarDays(12, year));
//
//        return Single.zip(observables, args -> {
//
//            List<OggyCalendarDay> list1 = new ArrayList<>();
//            for (Object arg : args) {
//                list1.addAll((List<OggyCalendarDay>) arg);
//            }
//
//            return list1;
//        });
//    }
//
//    private Single<List<OggyCalendarDay>> getCalendarDays(int month, int year) {
//
//        String login = mPreferenceUtils.getUserLogin();
//        String password = mPreferenceUtils.getUserPassword();
//
//        return ApiFactory.getOggyService()
//                .getCalendarDays(login, password, month, year)
//                .lift(ApiFactory.getApiErrorTransformer())
//                .map(calendarDays -> {
//
//                    CalendarDayDAO.saveCalendarDays(calendarDays);
//
//                    return calendarDays;
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
//}
