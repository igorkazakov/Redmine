package com.igorkazakov.user.redminepro.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 14.07.17.
 */

public class DateUtils {

    public static SimpleDateFormat getSimpleFormatter() {

        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public static SimpleDateFormat getDateFormatterWithTime() {

        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    }

    public static Date dateFromString(String string, SimpleDateFormat format) {

        try {
            return format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String stringFromDate(Date date, SimpleDateFormat format) {

        return format.format(date);
    }

    public static Date getMonday(Date date) {

        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        int today = c.get(Calendar.DAY_OF_WEEK);
        c.add(Calendar.DAY_OF_WEEK, -today + Calendar.MONDAY);
        return c.getTime();
    }

    public static TimeInterval getPreviousWeekInterval() {

        int daysForPreviousSunday = -1;
        int daysForPreviousMonday = -6;

        Calendar c = Calendar.getInstance();
        c.setTime(DateUtils.getMonday(new Date()));
        c.add(Calendar.DATE, daysForPreviousSunday);
        Date end = c.getTime();
        c.add(Calendar.DATE, daysForPreviousMonday);
        Date start = c.getTime();

        return new TimeInterval(start, end);
    }

    public static TimeInterval getCurrentWeekInterval() {

        int daysForCurrentSunday = 6;
        Calendar c = Calendar.getInstance();
        c.setTime(DateUtils.getMonday(new Date()));
        Date end = c.getTime();
        c.add(Calendar.DATE, daysForCurrentSunday);
        Date start = c.getTime();

        return new TimeInterval(start, end);
    }

    public static TimeInterval getIntervalFromStartYear() {

        Date currentDate = new Date();
        int year= currentDate.getYear();
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.YEAR,year);
        calendarStart.set(Calendar.MONTH,0);
        calendarStart.set(Calendar.DAY_OF_MONTH,1);
        Date startDate = calendarStart.getTime();

        return new TimeInterval(startDate, currentDate);
    }
}
