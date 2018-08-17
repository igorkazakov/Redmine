package com.igorkazakov.user.redminepro.utils;

import com.igorkazakov.user.redminepro.database.realm.CalendarDayDAO;
import com.igorkazakov.user.redminepro.database.realm.TimeEntryDAO;
import com.igorkazakov.user.redminepro.database.room.dao.OggyCalendarDayDAO;
import com.igorkazakov.user.redminepro.models.TimeInterval;
import com.igorkazakov.user.redminepro.models.TimeModel;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 14.07.17.
 */

public class KPIUtils {

    public static TimeModel getHoursForYear() {
        TimeInterval interval = DateUtils.getIntervalFromStartYear();
        return TimeEntryDAO.getWorkHoursWithInterval(interval);
    }

    public static TimeModel getHoursForCurrentMonth() {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        TimeInterval interval = DateUtils.getMonthInterval(month);
        return TimeEntryDAO.getWorkHoursWithInterval(interval);
    }

    public static TimeModel getHoursForYesterday() {
        Date date = DateUtils.getYesterday();
        return TimeEntryDAO.getWorkHoursWithDate(date);
    }

    public static TimeModel getHoursForToday() {
        Date date = new Date();
        return TimeEntryDAO.getWorkHoursWithDate(date);
    }

    public static TimeModel getHoursForDate(Date date) {
        return TimeEntryDAO.getWorkHoursWithDate(date);
    }

    public static float calculateKpiForYear() {

        TimeInterval interval = DateUtils.getIntervalFromStartYear();
        TimeModel model = TimeEntryDAO.getWorkHoursWithInterval(interval);
        float norm = OggyCalendarDayDAO.getHoursNormForInterval(interval.getStart().getTime(), interval.getEnd().getTime());

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public static float calculateKpiForCurrentMonth() {

        TimeInterval interval = DateUtils.getCurrentMonthInterval();
        TimeModel model = getHoursForCurrentMonth();
        float norm = CalendarDayDAO.getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public static float calculateKpiForDate(Date date) {

        TimeModel model = TimeEntryDAO.getWorkHoursWithDate(date);
        float norm = CalendarDayDAO.getHoursNormForDate(date);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public static float calculateKpiForCurrentWeek() {

        TimeInterval interval = DateUtils.getCurrentWeekInterval();
        TimeModel model = TimeEntryDAO.getWorkHoursWithInterval(interval);
        float norm = CalendarDayDAO.getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public static TimeModel getHoursForPreviousWeek() {
        TimeInterval interval = DateUtils.getPreviousWeekInterval();
        return TimeEntryDAO.getWorkHoursWithInterval(interval);
    }

    public static TimeModel getHoursForCurrentWeek() {
        TimeInterval interval = DateUtils.getCurrentWeekInterval();
        return TimeEntryDAO.getWorkHoursWithInterval(interval);
    }


    public static float calculateKpiForPreviousWeek() {
        TimeInterval interval = DateUtils.getPreviousWeekInterval();
        TimeModel model = TimeEntryDAO.getWorkHoursWithInterval(interval);
        float norm = CalendarDayDAO.getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }
}
