package com.igorkazakov.user.redminepro.utils;

import com.igorkazakov.user.redminepro.database.DatabaseManager;
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
        return DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
    }

    public static TimeModel getHoursForCurrentMonth() {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        TimeInterval interval = DateUtils.getMonthInterval(month);
        return DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
    }

    public static TimeModel getHoursForYesterday() {
        Date date = DateUtils.getYesterday();
        return DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithDate(date);
    }

    public static TimeModel getHoursForToday() {
        Date date = new Date();
        return DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithDate(date);
    }

    public static TimeModel getHoursForDate(Date date) {
        return DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithDate(date);
    }

    public static float calculateKpiForYear() {

        TimeInterval interval = DateUtils.getIntervalFromStartYear();
        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public static float calculateKpiForCurrentMonth() {

        TimeInterval interval = DateUtils.getCurrentMonthInterval();
        TimeModel model = getHoursForCurrentMonth();
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public static float calculateKpiForDate(Date date) {

        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithDate(date);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForDate(date);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public static float calculateKpiForCurrentWeek() {

        TimeInterval interval = DateUtils.getCurrentWeekInterval();
        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }

    public static TimeModel getHoursForPreviousWeek() {
        TimeInterval interval = DateUtils.getPreviousWeekInterval();
        return DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
    }

    public static TimeModel getHoursForCurrentWeek() {
        TimeInterval interval = DateUtils.getCurrentWeekInterval();
        return DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
    }


    public static float calculateKpiForPreviousWeek() {
        TimeInterval interval = DateUtils.getPreviousWeekInterval();
        TimeModel model = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getWorkHoursWithInterval(interval);
        float norm = DatabaseManager.getDatabaseHelper().getCalendarDayDAO().getHoursNormForInterval(interval);

        return NumberUtils.round((model.getRegularTime() + model.getTeamFuckupTime()) / norm);
    }
}
