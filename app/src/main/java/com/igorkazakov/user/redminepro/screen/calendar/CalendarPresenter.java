package com.igorkazakov.user.redminepro.screen.calendar;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.api.responseEntity.CalendarDay.OggyCalendarDay;
import com.igorkazakov.user.redminepro.database.room.entity.OggyCalendarDayEntity;
import com.igorkazakov.user.redminepro.utils.KPIUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Igor on 29.08.2017.
 */

@InjectViewState
public class CalendarPresenter extends MvpPresenter<CalendarView> implements LifecycleObserver {

    private CalendarServiceInterface mRepository;
    private KPIUtils mKPIUtils;
    private ArrayList<CalendarDay> listOfHoliday = new ArrayList<>();
    private ArrayList<CalendarDay> listOfHospital = new ArrayList<>();
    private ArrayList<CalendarDay> listOfVacation = new ArrayList<>();
    private CompositeDisposable mDisposable = new CompositeDisposable();

    public CalendarPresenter(CalendarServiceInterface repository, KPIUtils kpiUtils) {
        mRepository = repository;
        mKPIUtils = kpiUtils;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadAllCalendarDays();
    }

    public void loadAllCalendarDays() {

        mDisposable.add(mRepository.getCalendarDaysForYearFromBd()
                .doOnSubscribe(__ -> getViewState().showLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::createMonthIndicatorArrays,
                        throwable -> {
                            ApiException exception = (ApiException)throwable;
                            getViewState().showError(exception);
                        }));
    }

    public void onDateClick(Date day) {

        mDisposable.add(mKPIUtils.getStatisticForDay(day)
                .subscribe(result -> {
            getViewState().showDayWorkHours(result.getKpi(), result.getTimeModel());
        }));
    }

    private void createMonthIndicatorArrays(List<OggyCalendarDayEntity> response) {

        for (OggyCalendarDayEntity day : response) {

            switch (day.getType()) {
                case OggyCalendarDay.FEAST:
                case OggyCalendarDay.HOLIDAY:
                    listOfHoliday.add(CalendarDay.from(day.getDate()));
                    break;

                case OggyCalendarDay.HOSPITAL:
                    listOfHospital.add(CalendarDay.from(day.getDate()));
                    break;

                case OggyCalendarDay.VACATION:
                    listOfVacation.add(CalendarDay.from(day.getDate()));
                    break;
            }
        }

        getViewState().showMonthIndicators(listOfHoliday, listOfHospital, listOfVacation);
        getViewState().showCurrentDay();
        getViewState().hideLoading();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onActivityDestroy() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
