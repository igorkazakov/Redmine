package com.igorkazakov.user.redminepro.screen.calendar;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.api.responseEntity.CalendarDay.OggyCalendarDay;
import com.igorkazakov.user.redminepro.models.TimeModel;
import com.igorkazakov.user.redminepro.utils.KPIUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Igor on 29.08.2017.
 */

@InjectViewState
public class CalendarPresenter extends MvpPresenter<CalendarView> implements LifecycleObserver {

    private CalendarServiceInterface mRepository;
    private ArrayList<CalendarDay> listOfHoliday = new ArrayList<>();
    private ArrayList<CalendarDay> listOfHospital = new ArrayList<>();
    private ArrayList<CalendarDay> listOfVacation = new ArrayList<>();
    private Disposable mDisposable;

    public CalendarPresenter(CalendarServiceInterface repository) {
        mRepository = repository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadAllCalendarDays();
    }

    public void loadAllCalendarDays() {

        mDisposable = mRepository.getCalendarDaysForYear()
                .doOnSubscribe(__ -> getViewState().showLoading())
                .subscribe(this::createMonthIndicatorArrays,
                        throwable -> {
                            ApiException exception = (ApiException)throwable;
                            getViewState().showError(exception);
                        });
    }

    public void onDateClick(Date day) {

        TimeModel model = KPIUtils.getHoursForDate(day);
        float kpi = KPIUtils.calculateKpiForDate(day);
        getViewState().showDayWorkHours(kpi, model);
    }

    private void createMonthIndicatorArrays(List<OggyCalendarDay> response) {

        for (OggyCalendarDay day : response) {

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
