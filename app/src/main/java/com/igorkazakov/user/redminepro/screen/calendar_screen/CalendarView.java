package com.igorkazakov.user.redminepro.screen.calendar_screen;

import com.igorkazakov.user.redminepro.models.TimeModel;
import com.igorkazakov.user.redminepro.screen.base.BaseViewInterface;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;

/**
 * Created by Igor on 29.08.2017.
 */

public interface CalendarView extends BaseViewInterface {

    void showMonthIndicators(ArrayList<CalendarDay> listOfHoliday,
                       ArrayList<CalendarDay> listOfHospital,
                       ArrayList<CalendarDay> listOfVacation);

    void showDayWorkHours(float kpi, TimeModel model);
    void showCurrentDay();
}
