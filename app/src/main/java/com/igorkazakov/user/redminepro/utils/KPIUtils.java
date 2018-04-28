package com.igorkazakov.user.redminepro.utils;

import com.igorkazakov.user.redminepro.database.realm.CalendarDayRealmDAO;
import com.igorkazakov.user.redminepro.database.realm.TimeEntryRealmDAO;
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
        return TimeEntryRealmDAO.getWorkHoursWithInterval(interval);
    }

    public static TimeModel getHoursForCurrentMonth() {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        TimeInterval interval = DateUtils.getMonthInterval(month);
        return TimeEntryRealmDAO.getWorkHoursWithInterval(interval);
    }

    public static TimeModel getHoursForYesterday() {
        Date date = DateUtils.getYesterday();
        return TimeEntryRealmDAO.getWorkHoursWithDate(date);
    }

    public static TimeModel getHoursForToday() {
        Date date = new Date();
        return TimeEntryRealmDAO.getWorkHoursWithDate(date);
    }

    public static TimeModel getHoursForDate(Date date) {
        return TimeEntryRealmDAO.getWorkHoursWithDate(date);
    }

    public static float calculateKpiForYear() {

        TimeInterval interval = DateUtils.getIntervalFromStartYear();
        TimeModel model = TimeEntryRealmDAO.getWorkHoursWithInterval(interval);
        float norm = CalendarDayRealmDAO.getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public static float calculateKpiForCurrentMonth() {

        TimeInterval interval = DateUtils.getCurrentMonthInterval();
        TimeModel model = getHoursForCurrentMonth();
        float norm = CalendarDayRealmDAO.getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public static float calculateKpiForDate(Date date) {

        TimeModel model = TimeEntryRealmDAO.getWorkHoursWithDate(date);
        float norm = CalendarDayRealmDAO.getHoursNormForDate(date);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public static float calculateKpiForCurrentWeek() {

        TimeInterval interval = DateUtils.getCurrentWeekInterval();
        TimeModel model = TimeEntryRealmDAO.getWorkHoursWithInterval(interval);
        float norm = CalendarDayRealmDAO.getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public static TimeModel getHoursForPreviousWeek() {
        TimeInterval interval = DateUtils.getPreviousWeekInterval();
        return TimeEntryRealmDAO.getWorkHoursWithInterval(interval);
    }

    public static TimeModel getHoursForCurrentWeek() {
        TimeInterval interval = DateUtils.getCurrentWeekInterval();
        return TimeEntryRealmDAO.getWorkHoursWithInterval(interval);
    }


    public static float calculateKpiForPreviousWeek() {
        TimeInterval interval = DateUtils.getPreviousWeekInterval();
        TimeModel model = TimeEntryRealmDAO.getWorkHoursWithInterval(interval);
        float norm = CalendarDayRealmDAO.getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }
}
