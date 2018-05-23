package com.igorkazakov.user.redminepro.screen.dashboard;

import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.api.responseEntity.CalendarDay.OggyCalendarDay;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.TimeEntry;
import com.igorkazakov.user.redminepro.repository.OggyRepository;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DashboardPresenterTest {

    @Mock
    RedmineRepository redmineRepository;

    @Mock
    OggyRepository oggyRepository;

    @Mock
    DashboardView dashboardView;

    DashboardPresenter presenter;

    public DashboardPresenterTest() {
        super();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = spy(new DashboardPresenter(redmineRepository, oggyRepository));
        doNothing().when(presenter).onFirstViewAttach();

        presenter.attachView(dashboardView);
    }

    @Test
    public void tryLoadDashboardData() {

        List<OggyCalendarDay> calendarDays = new ArrayList<>();
        when(oggyRepository.getCalendarDaysForYear()).thenReturn(Observable.fromArray(calendarDays));

        List<TimeEntry> timeEntries = new ArrayList<>();
        when(redmineRepository.getTimeEntriesForYear()).thenReturn(Observable.fromArray(timeEntries));

        presenter.tryLoadDashboardData();

        verify(dashboardView).showLoading();
        verify(dashboardView, times(2)).hideLoading();
        verify(dashboardView).setupCurrentWeekStatistic(Mockito.anyFloat(),
                Mockito.anyFloat(),
                Mockito.anyFloat());

        verify(dashboardView).setupChart(Mockito.any(), Mockito.anyFloat());

        verify(dashboardView).setupStatisticRecyclerView(Mockito.any());

    }

    @Test
    public void failLoadDashboardData() {

        ApiException exception = new ApiException("exception", 9999);
        when(oggyRepository.getCalendarDaysForYear()).thenReturn(Observable.error(exception));

        List<TimeEntry> timeEntries = new ArrayList<>();
        when(redmineRepository.getTimeEntriesForYear()).thenReturn(Observable.fromArray(timeEntries));

        presenter.tryLoadDashboardData();

        verify(dashboardView).showLoading();
        verify(dashboardView).showError(exception);
        verify(dashboardView).setupCurrentWeekStatistic(Mockito.anyFloat(),
                Mockito.anyFloat(),
                Mockito.anyFloat());

        verify(dashboardView).setupChart(Mockito.any(), Mockito.anyFloat());

        verify(dashboardView).setupStatisticRecyclerView(Mockito.any());
    }

    @Test
    public void failLoadAll() {

        ApiException exception = new ApiException("exception", 9999);
        when(oggyRepository.getCalendarDaysForYear()).thenReturn(Observable.error(exception));
        when(redmineRepository.getTimeEntriesForYear()).thenReturn(Observable.error(exception));

        presenter.tryLoadDashboardData();

        verify(dashboardView).showLoading();
        verify(dashboardView, times(2)).showError(exception);
        verify(dashboardView).hideLoading();
    }

    @Test
    public void getWholeCurrentWeekHoursNorm() {
    }

    @Test
    public void remainHoursForNormalKpi() {
    }

    @Test
    public void remainHoursForWeek() {
    }
}