package com.igorkazakov.user.redminepro.utils;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.application.RedmineApplication;
import com.igorkazakov.user.redminepro.models.ChartModel;
import com.igorkazakov.user.redminepro.models.StatisticModel;
import com.igorkazakov.user.redminepro.models.TimeInterval;
import com.igorkazakov.user.redminepro.models.TimeModel;
import com.igorkazakov.user.redminepro.repository.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by user on 14.07.17.
 */

public class KPIUtils {

    private static volatile KPIUtils sInstance;

    @Inject
    Repository mRepository;

    @Inject
    RedmineApplication mApplication;

    private KPIUtils() {

        RedmineApplication.getComponent().inject(this);
    }

    public static KPIUtils getInstance() {

        KPIUtils localService = sInstance;
        if (localService == null) {
            synchronized (KPIUtils.class) {
                localService = sInstance;
                if (localService == null) {
                    localService = sInstance = new KPIUtils();
                }
            }
        }

        return localService;
    }

    public Single<ChartModel> getChartData() {
        return Single.zip(getHoursForYear(),
                calculateKpiForYear(),
                ChartModel::new)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<StatisticModel> getStatisticForDay(Date date) {

        return Single.zip(getHoursForDate(date),
                calculateKpiForDate(date),
                (model, kpi) -> {
                    return new StatisticModel(model,
                            kpi,
                            mApplication.getResources().getString(R.string.title_today));

                }).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<StatisticModel>> getStatistics() {

        List<Single<StatisticModel>> observables = new ArrayList<>();

        observables.add(getStatisticForCurrentMonth());
        observables.add(getStatisticForPreviousWeek());
        observables.add(getStatisticForCurrentWeek());
        observables.add(getStatisticForYesterday());
        observables.add(getStatisticForDay(new Date()));

        return Single.zip(observables, args -> {

            List<StatisticModel> list1 = new ArrayList<>();
            for (Object arg : args) {
                list1.add((StatisticModel) arg);
            }

            return list1;

        }).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<TimeModel> getHoursForToday() {
        Date date = new Date();
        return mRepository.workHoursWithDate(date);
    }

    private Single<TimeModel> getHoursForYear() {
        TimeInterval interval = DateUtils.getIntervalFromStartYear();
        return mRepository.workHoursWithInterval(interval);
    }

    private Single<Float> calculateKpiForYear() {

        TimeInterval interval = DateUtils.getIntervalFromStartYear();

        return Single.zip(mRepository.workHoursWithInterval(interval),
                mRepository.hoursNormForInterval(interval),
                (timeModel, norm) -> {
                    return NumberUtils.round((timeModel.getRegularTime() + timeModel.getTeamFuckupTime()) / norm);
                });
    }

    private Single<TimeModel> getHoursForCurrentMonth() {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        TimeInterval interval = DateUtils.getMonthInterval(month);
        return mRepository.workHoursWithInterval(interval);
    }

    private Single<TimeModel> getHoursForYesterday() {
        Date date = DateUtils.getYesterday();
        return mRepository.workHoursWithDate(date);
    }

    private Single<TimeModel> getHoursForDate(Date date) {
        return mRepository.workHoursWithDate(date);
    }

    private Single<Float> calculateKpiForDate(Date date) {

        return Single.zip(mRepository.workHoursWithDate(date),
                mRepository.hoursNormForDate(date),
                (timeModel, norm) -> {
                    return NumberUtils.round((timeModel.getRegularTime() + timeModel.getTeamFuckupTime()) / norm);
                });
    }

    private Single<Float> calculateKpiForCurrentMonth() {

        TimeInterval interval = DateUtils.getCurrentMonthInterval();
        return Single.zip(mRepository.workHoursWithInterval(interval),
                mRepository.hoursNormForInterval(interval),
                (timeModel, norm) -> {
                    return NumberUtils.round((timeModel.getRegularTime() + timeModel.getTeamFuckupTime()) / norm);
                });
    }

    private Single<Float> calculateKpiForCurrentWeek() {

        TimeInterval interval = DateUtils.getCurrentWeekInterval();

        return Single.zip(mRepository.workHoursWithInterval(interval),
                mRepository.hoursNormForInterval(interval),
                (timeModel, norm) -> {
                    return NumberUtils.round((timeModel.getRegularTime() + timeModel.getTeamFuckupTime()) / norm);
                });
    }

    private Single<TimeModel> getHoursForPreviousWeek() {
        TimeInterval interval = DateUtils.getPreviousWeekInterval();
        return mRepository.workHoursWithInterval(interval);
    }

    private Single<TimeModel> getHoursForCurrentWeek() {
        TimeInterval interval = DateUtils.getCurrentWeekInterval();
        return mRepository.workHoursWithInterval(interval);
    }

    private Single<Float> calculateKpiForPreviousWeek() {
        TimeInterval interval = DateUtils.getPreviousWeekInterval();

        return Single.zip(mRepository.workHoursWithInterval(interval),
                mRepository.hoursNormForInterval(interval),
                (model, norm) -> {
                    return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
                });
    }

    private Single<StatisticModel> getStatisticForCurrentMonth() {

        return Single.zip(getHoursForCurrentMonth(),
                calculateKpiForCurrentMonth(),
                (model, kpi) -> {
                    return new StatisticModel(model,
                            kpi,
                            mApplication.getResources().getString(R.string.title_current_month));
                });
    }

    private Single<StatisticModel> getStatisticForPreviousWeek() {

        return Single.zip(getHoursForPreviousWeek(),
                calculateKpiForPreviousWeek(),
                (model, kpi) -> {
                    return new StatisticModel(model,
                            kpi,
                            mApplication.getResources().getString(R.string.title_previous_week));
                });
    }

    private Single<StatisticModel> getStatisticForCurrentWeek() {

        return Single.zip(getHoursForCurrentWeek(),
                calculateKpiForCurrentWeek(),
                (model, kpi) -> {
                    return new StatisticModel(model,
                            kpi,
                            mApplication.getResources().getString(R.string.title_current_week));
                });
    }

    private Single<StatisticModel> getStatisticForYesterday() {

        return Single.zip(getHoursForYesterday(),
                calculateKpiForDate(DateUtils.getYesterday()),
                (model, kpi) -> {
                    return new StatisticModel(model,
                            kpi,
                            mApplication.getResources().getString(R.string.title_yesterday));
                });
    }
}
