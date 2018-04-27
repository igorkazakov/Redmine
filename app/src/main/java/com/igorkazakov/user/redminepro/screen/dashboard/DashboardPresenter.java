package com.igorkazakov.user.redminepro.screen.dashboard;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.BuildConfig;
import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.models.StatisticModel;
import com.igorkazakov.user.redminepro.models.TimeInterval;
import com.igorkazakov.user.redminepro.models.TimeModel;
import com.igorkazakov.user.redminepro.repository.OggyRepository;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.igorkazakov.user.redminepro.utils.KPIUtils;
import com.igorkazakov.user.redminepro.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.arturvasilov.rxloader.LifecycleHandler;

/**
 * Created by user on 12.07.17.
 */

public class DashboardPresenter {

    private LifecycleHandler mLifecycleHandler;
    private DashboardView mView;
    private boolean mIsLoading = false;

    public  DashboardPresenter(@NonNull LifecycleHandler lifecycleHandler, @NonNull DashboardView view) {

        mLifecycleHandler = lifecycleHandler;
        mView = view;
    }

    public void loadRedmineData() {
        RedmineRepository.getStatuses()
                .compose(mLifecycleHandler.reload(R.id.statuses_request))
                .subscribe();
        RedmineRepository.getTrackers()
                .compose(mLifecycleHandler.reload(R.id.trackers_request))
                .subscribe();
        RedmineRepository.getProjectPriorities()
                .compose(mLifecycleHandler.reload(R.id.priorities_request))
                .subscribe();
        RedmineRepository.getProjects()
                .compose(mLifecycleHandler.reload(R.id.projects_request))
                .subscribe();
    }

    public void tryLoadDashboardData() {

        if (!mIsLoading) {
            mIsLoading = true;

            OggyRepository.getCalendarDaysForYear()
                        .doOnSubscribe(mView::showLoading)
                        .compose(mLifecycleHandler.reload(R.id.calendar_days_request))
                        .subscribe(response -> loadTimeEntriesData(),
                                Throwable::printStackTrace);
        }
    }

    public void loadTimeEntriesData() {

            RedmineRepository.getTimeEntriesForYear()
                    .doOnTerminate(mView::hideLoading)
                    .doOnNext(__ -> mView.hideLoading())
                    .compose(mLifecycleHandler.reload(R.id.time_entry_request))
                    .subscribe(response -> setupView(),
                            throwable -> throwable.printStackTrace());
    }

    public void setupView() {

        mIsLoading = false;
        mView.setupCurrentWeekStatistic(remainHoursForNormalKpi(),
                remainHoursForWeek(), getWholeCurrentWeekHoursNorm());
        mView.setupChart(KPIUtils.getHoursForYear(), KPIUtils.calculateKpiForYear());
        mView.setupStatisticRecyclerView(getStatistics());
    }

    public List<StatisticModel> getStatistics() {

        List<StatisticModel> timeModelList = new ArrayList<>();
        timeModelList.add(new StatisticModel(KPIUtils.getHoursForCurrentMonth(),
                KPIUtils.calculateKpiForCurrentMonth(), "Current month"));
        timeModelList.add(new StatisticModel(KPIUtils.getHoursForPreviousWeek(),
                KPIUtils.calculateKpiForPreviousWeek(), "Previous week"));
        timeModelList.add(new StatisticModel(KPIUtils.getHoursForCurrentWeek(),
                KPIUtils.calculateKpiForCurrentWeek(), "Current week"));
        timeModelList.add(new StatisticModel(KPIUtils.getHoursForYesterday(),
                KPIUtils.calculateKpiForDate(DateUtils.getYesterday()), "Yesterday"));
        timeModelList.add(new StatisticModel(KPIUtils.getHoursForToday(),
                KPIUtils.calculateKpiForDate(new Date()), "Today"));

        return timeModelList;
    }

    public float getWholeCurrentWeekHoursNorm() {
        TimeInterval interval = DateUtils.getCurrentWholeWeekInterval();
        return DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);
    }

    public float remainHoursForNormalKpi() {

        TimeInterval interval = DateUtils.getCurrentWholeWeekInterval();
        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);
        float remainHours = NumberUtils.round(norm * BuildConfig.NORMAL_KPI -
                (model.getRegularTime() + model.getTeamFuckupTime()));
        if (remainHours > 0) {
            return remainHours;

        } else {
            return 0;
        }
    }

    public float remainHoursForWeek() {

        TimeInterval interval = DateUtils.getCurrentWholeWeekInterval();
        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);
        float remainHours = norm - (model.getRegularTime() + model.getFuckupTime() + model.getTeamFuckupTime());
        if (remainHours > 0) {
            return remainHours;

        } else {
            return 0;
        }
    }
}
