package com.igorkazakov.user.redminepro.screen.dashboard;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.igorkazakov.user.redminepro.BuildConfig;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.models.StatisticModel;
import com.igorkazakov.user.redminepro.models.TimeInterval;
import com.igorkazakov.user.redminepro.models.TimeModel;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.igorkazakov.user.redminepro.utils.KPIUtils;
import com.igorkazakov.user.redminepro.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by user on 12.07.17.
 */

@InjectViewState
public class DashboardPresenter extends MvpPresenter<DashboardView> implements LifecycleObserver {

    private DashboardServiceInterface mRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public DashboardPresenter(DashboardServiceInterface repository) {
        mRepository = repository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        loadRedmineData();
        tryLoadDashboardData();
    }

    public void tryLoadDashboardData() {

        Disposable disposable = mRepository.getCalendarDaysForYear()
                .doOnSubscribe(__ -> getViewState().showLoading())
                .subscribe(response -> loadTimeEntriesData(),
                        throwable -> {
                            loadTimeEntriesData();
                            ApiException exception = (ApiException) throwable;
                            getViewState().showError(exception);
                        });

        mCompositeDisposable.add(disposable);
    }

    public void setupView() {

        getViewState().setupCurrentWeekStatistic(remainHoursForNormalKpi(),
                remainHoursForWeek(), getWholeCurrentWeekHoursNorm());
        getViewState().setupChart(KPIUtils.getHoursForYear(), KPIUtils.calculateKpiForYear());
        getViewState().setupStatisticRecyclerView(getStatistics());
    }

    private void loadRedmineData() {
        mRepository.getStatuses().subscribe();
        mRepository.getTrackers().subscribe();
        mRepository.getProjectPriorities().subscribe();
        mRepository.getProjects().subscribe();
    }

    private void loadTimeEntriesData() {

        Disposable disposable = mRepository.getTimeEntriesForYear()
                .doOnTerminate(getViewState()::hideLoading)
                .doOnNext(__ -> getViewState().hideLoading())
                .subscribe(response -> setupView(),
                        throwable -> {
                            ApiException exception = (ApiException) throwable;
                            getViewState().showError(exception);
                        });

        mCompositeDisposable.add(disposable);
    }

    private List<StatisticModel> getStatistics() {

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

    private float getWholeCurrentWeekHoursNorm() {
        TimeInterval interval = DateUtils.getCurrentWholeWeekInterval();
        return mRepository.fetchHoursNormForInterval(interval);
    }

    private float remainHoursForNormalKpi() {

        TimeInterval interval = DateUtils.getCurrentWholeWeekInterval();
        TimeModel model = mRepository.fetchWorkHoursWithInterval(interval);
        float norm = mRepository.fetchHoursNormForInterval(interval);
        float remainHours = NumberUtils.round(norm * BuildConfig.NORMAL_KPI -
                (model.getRegularTime() + model.getTeamFuckupTime()));
        if (remainHours > 0) {
            return remainHours;

        } else {
            return 0;
        }
    }

    private float remainHoursForWeek() {

        TimeInterval interval = DateUtils.getCurrentWholeWeekInterval();
        TimeModel model = mRepository.fetchWorkHoursWithInterval(interval);
        float norm = mRepository.fetchHoursNormForInterval(interval);
        float remainHours = norm - (model.getRegularTime() + model.getFuckupTime() + model.getTeamFuckupTime());
        if (remainHours > 0) {
            return remainHours;

        } else {
            return 0;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onActivityDestroy() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }
}
