package com.igorkazakov.user.redminepro.screen.dashboard;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.igorkazakov.user.redminepro.utils.NumberUtils;
import com.igorkazakov.user.redminepro.utils.TimeInterval;
import com.igorkazakov.user.redminepro.utils.TimeModel;

import java.util.Calendar;

import ru.arturvasilov.rxloader.LifecycleHandler;

/**
 * Created by user on 12.07.17.
 */

public class DashboardPresenter {

    private LifecycleHandler mLifecycleHandler;
    private DashboardView mView;
    private float normalKpi = 0.7f;

    public float getNormalKpi() {
        return normalKpi;
    }

    public  DashboardPresenter(@NonNull LifecycleHandler lifecycleHandler, @NonNull DashboardView view) {

        mLifecycleHandler = lifecycleHandler;
        mView = view;
    }

    public void tryLoadDashboardData() {

        RedmineRepository.loadDashboardScreenData()
                .doOnSubscribe(mView::showLoading)
                .doOnTerminate(mView::hideLoading)
                .compose(mLifecycleHandler.reload(R.id.auth_request))
                .subscribe();
    }

    public TimeModel getHoursForYear() {
        TimeInterval interval = DateUtils.getIntervalFromStartYear();
        return DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
    }

    public float calculateKpiForYear() {

        TimeInterval interval = DateUtils.getIntervalFromStartYear();
        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public float calculateKpiForCurrentMonth() {

        TimeInterval interval = DateUtils.getMonthInterval(Calendar.getInstance().get(Calendar.MONTH));
        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public float calculateKpiForCurrentWeek() {

        TimeInterval interval = DateUtils.getCurrentWeekInterval();
        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public float calculateKpiForPreviousWeek() {

        TimeInterval interval = DateUtils.getPreviousWeekInterval();
        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public float remainHoursForNormalKpi() {

        TimeInterval interval = DateUtils.getCurrentWeekInterval();
        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);

        return norm / normalKpi - (model.getRegularTime() + model.getTeamFuckupTime());
    }

    public float remainDaysForWeek() {

        TimeInterval interval = DateUtils.getCurrentWeekInterval();
        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);
        return norm - (model.getRegularTime() + model.getTeamFuckupTime() + model.getTeamFuckupTime());
    }
}
