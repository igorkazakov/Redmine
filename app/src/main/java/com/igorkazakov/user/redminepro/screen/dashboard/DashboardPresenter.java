package com.igorkazakov.user.redminepro.screen.dashboard;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.igorkazakov.user.redminepro.BuildConfig;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.database.realm.CalendarDayDAO;
import com.igorkazakov.user.redminepro.database.realm.TimeEntryDAO;
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

/**
 * Created by user on 12.07.17.
 */

@InjectViewState
public class DashboardPresenter extends MvpPresenter<DashboardView> {

    RedmineRepository mRedmineRepository;
    OggyRepository mOggyRepository;

    private boolean mIsLoading = false;

    public DashboardPresenter(RedmineRepository redmineRepository,
                              OggyRepository oggyRepository) {

        mRedmineRepository = redmineRepository;
        mOggyRepository = oggyRepository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        loadRedmineData();
        tryLoadDashboardData();
    }

    public void loadRedmineData() {
        mRedmineRepository.getStatuses().subscribe();
        mRedmineRepository.getTrackers().subscribe();
        mRedmineRepository.getProjectPriorities().subscribe();
        mRedmineRepository.getProjects().subscribe();
    }

    public void tryLoadDashboardData() {

        if (!mIsLoading) {
            mIsLoading = true;

            mOggyRepository.getCalendarDaysForYear()
                        .doOnSubscribe(__ -> getViewState().showLoading())
                        .subscribe(response -> loadTimeEntriesData(),
                                throwable -> {
                                    loadTimeEntriesData();
                                    ApiException exception = (ApiException)throwable;
                                    getViewState().showError(exception);
                                });
        }
    }

    public void loadTimeEntriesData() {

        mRedmineRepository.getTimeEntriesForYear()
                    .doOnTerminate(getViewState()::hideLoading)
                    .doOnNext(__ -> getViewState().hideLoading())
                    .subscribe(response -> setupView(),
                            throwable -> {
                                ApiException exception = (ApiException)throwable;
                                getViewState().showError(exception);
                            });
    }

    public void setupView() {

        mIsLoading = false;
        getViewState().setupCurrentWeekStatistic(remainHoursForNormalKpi(),
                remainHoursForWeek(), getWholeCurrentWeekHoursNorm());
        getViewState().setupChart(KPIUtils.getHoursForYear(), KPIUtils.calculateKpiForYear());
        getViewState().setupStatisticRecyclerView(getStatistics());
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
        return CalendarDayDAO.getHoursNormForInterval(interval);
    }

    public float remainHoursForNormalKpi() {

        TimeInterval interval = DateUtils.getCurrentWholeWeekInterval();
        TimeModel model = TimeEntryDAO.getWorkHoursWithInterval(interval);
        float norm = CalendarDayDAO.getHoursNormForInterval(interval);
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
        TimeModel model = TimeEntryDAO.getWorkHoursWithInterval(interval);
        float norm = CalendarDayDAO.getHoursNormForInterval(interval);
        float remainHours = norm - (model.getRegularTime() + model.getFuckupTime() + model.getTeamFuckupTime());
        if (remainHours > 0) {
            return remainHours;

        } else {
            return 0;
        }
    }
}
