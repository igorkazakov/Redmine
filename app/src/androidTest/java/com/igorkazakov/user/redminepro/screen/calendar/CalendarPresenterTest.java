package com.igorkazakov.user.redminepro.screen.calendar;

import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.api.responseEntity.CalendarDay.OggyCalendarDay;
import com.igorkazakov.user.redminepro.repository.OggyRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalendarPresenterTest {

    CalendarPresenter calendarPresenter;

    @Mock
    CalendarView calendarView;

    @Mock
    OggyRepository repository;

    public CalendarPresenterTest() {
        super();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        calendarPresenter = new CalendarPresenter(repository);
        when(repository.getCalendarDaysForYear())
               .thenReturn(Observable.fromArray(new ArrayList<>()));
        //ApiException exception = new ApiException("error", 9999);
        // when(repository.getCalendarDaysForYear()).thenReturn(Observable.error(exception));
        calendarPresenter.attachView(calendarView);
    }

    @Test
    public void successLoadAllCalendarDays() {

        List<OggyCalendarDay> calendarDays = new ArrayList<>();
        when(repository.getCalendarDaysForYear()).thenReturn(Observable.fromArray(calendarDays));

        calendarPresenter.loadAllCalendarDays();
        verify(calendarView).showLoading();
        verify(calendarView).showCurrentDay();
        verify(calendarView).hideLoading();
    }

    @Test
    public void failLoadAllCalendarDays() {

        ApiException exception = new ApiException("error", 9999);
        when(repository.getCalendarDaysForYear()).thenReturn(Observable.error(exception));
        calendarPresenter.loadAllCalendarDays();
        verify(calendarView).showLoading();
        verify(calendarView).showError(exception);
        verify(calendarView).hideLoading();
    }
}