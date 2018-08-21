package com.igorkazakov.user.redminepro.screen.dashboard;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.igorkazakov.user.redminepro.BuildConfig;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.models.TimeInterval;
import com.igorkazakov.user.redminepro.models.TimeModel;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.igorkazakov.user.redminepro.utils.KPIUtils;
import com.igorkazakov.user.redminepro.utils.NumberUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by user on 12.07.17.
 */

@InjectViewState
public class DashboardPresenter extends MvpPresenter<DashboardView> implements LifecycleObserver {

    private DashboardServiceInterface mRepository;
    private KPIUtils mKPIUtils;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    DashboardPresenter(DashboardServiceInterface repository, KPIUtils kpiUtils) {
        mRepository = repository;
        mKPIUtils = kpiUtils;
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
                remainHoursForWeek(),
                getWholeCurrentWeekHoursNorm());

        mCompositeDisposable.add(mKPIUtils.getChartData().subscribe(result -> {
            getViewState().setupChart(result.getTimeModel(), result.getKpi());
        }));

        mCompositeDisposable.add(mKPIUtils.getStatistics().subscribe(result -> {
            getViewState().setupStatisticRecyclerView(result);
        }));
    }

    private void loadRedmineData() {
        mCompositeDisposable.add(mRepository.getStatuses().subscribe());
        mCompositeDisposable.add(mRepository.getTrackers().subscribe());
        mCompositeDisposable.add(mRepository.getProjectPriorities().subscribe());
        mCompositeDisposable.add(mRepository.getProjects().subscribe());
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
