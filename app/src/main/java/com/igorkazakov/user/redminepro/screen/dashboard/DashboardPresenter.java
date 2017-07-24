package com.igorkazakov.user.redminepro.screen.dashboard;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.BuildConfig;
import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.models.TimeInterval;
import com.igorkazakov.user.redminepro.models.TimeModel;
import com.igorkazakov.user.redminepro.repository.OggyRepository;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.igorkazakov.user.redminepro.utils.NumberUtils;

import java.util.Calendar;
import java.util.Date;

import ru.arturvasilov.rxloader.LifecycleHandler;

/**
 * Created by user on 12.07.17.
 */

public class DashboardPresenter {

    private LifecycleHandler mLifecycleHandler;
    private DashboardView mView;

    public  DashboardPresenter(@NonNull LifecycleHandler lifecycleHandler, @NonNull DashboardView view) {

        mLifecycleHandler = lifecycleHandler;
        mView = view;
    }

    public void tryLoadDashboardData() {

        OggyRepository.getCalendarDaysForYear()
                .doOnSubscribe(mView::showLoading)
                .compose(mLifecycleHandler.reload(R.id.calendar_days_request))
                .subscribe(response -> loadTimeEntriesData(),
                        throwable -> throwable.printStackTrace());
    }

    public void loadTimeEntriesData() {

        RedmineRepository.getTimeEntriesForYear()
                .doOnTerminate(mView::hideLoading)
                .compose(mLifecycleHandler.reload(R.id.time_entry_request))
                .subscribe(response -> mView.setupView(),
                        throwable -> throwable.printStackTrace());
    }

    public TimeModel getHoursForYear() {
        TimeInterval interval = DateUtils.getIntervalFromStartYear();
        return DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
    }

    public TimeModel getHoursForCurrentMonth() {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        TimeInterval interval = DateUtils.getMonthInterval(month);
        return DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
    }

    public TimeModel getHoursForYesterday() {
        Date date = DateUtils.getYesterday();
        return DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithDate(date);
    }

    public TimeModel getHoursForToday() {
        Date date = new Date();
        return DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithDate(date);
    }

    public float calculateKpiForYear() {

        TimeInterval interval = DateUtils.getIntervalFromStartYear();
        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public float calculateKpiForCurrentMonth() {

        TimeInterval interval = DateUtils.getCurrentMonthInterval();
        TimeModel model = getHoursForCurrentMonth();
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public float calculateKpiForDate(Date date) {

        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithDate(date);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForDate(date);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public float calculateKpiForCurrentWeek() {

        TimeInterval interval = DateUtils.getCurrentWeekInterval();
        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public TimeModel getHoursForPreviousWeek() {
        TimeInterval interval = DateUtils.getPreviousWeekInterval();
        return DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
    }

    public TimeModel getHoursForCurrentWeek() {
        TimeInterval interval = DateUtils.getCurrentWeekInterval();
        return DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
    }


    public float calculateKpiForPreviousWeek() {
        TimeInterval interval = DateUtils.getPreviousWeekInterval();
        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public float getWholeCurrentWeekHoursNorm() {
        TimeInterval interval = DateUtils.getCurrentWholeWeekInterval();
        return DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);
    }

    public float remainHoursForNormalKpi() {

        TimeInterval interval = DateUtils.getCurrentWholeWeekInterval();
        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);

        return NumberUtils.round(norm * BuildConfig.NORMAL_KPI - (model.getRegularTime() + model.getTeamFuckupTime()));
    }

    public float remainDaysForWeek() {

        TimeInterval interval = DateUtils.getCurrentWholeWeekInterval();
        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);
        return norm - (model.getRegularTime() + model.getTeamFuckupTime() + model.getTeamFuckupTime());
    }
}
