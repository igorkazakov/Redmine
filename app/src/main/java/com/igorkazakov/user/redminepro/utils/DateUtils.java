package com.igorkazakov.user.redminepro.utils;

import com.igorkazakov.user.redminepro.models.TimeInterval;

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

    public static Date getYesterday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        return c.getTime();
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

        Calendar c = Calendar.getInstance();
        c.setTime(DateUtils.getMonday(new Date()));
        Date start = c.getTime();
        Date end = new Date();

        return new TimeInterval(start, end);
    }

    public static TimeInterval getCurrentMonthInterval() {

        Date start, end;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        start = calendar.getTime();

        end = new Date();
        return new TimeInterval(start, end);
    }

    public static TimeInterval getCurrentWholeWeekInterval() {

        Calendar c = Calendar.getInstance();
        c.setTime(DateUtils.getMonday(new Date()));
        Date start = c.getTime();
        c.add(Calendar.DAY_OF_WEEK, 6);
        Date end = c.getTime();
        return new TimeInterval(start, end);
    }

    public static TimeInterval getMonthInterval(int month) {

        Date start, end;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MONTH, month);

        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        start = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        end = calendar.getTime();

        return new TimeInterval(start, end);
    }

    public static TimeInterval getIntervalFromStartYear() {

        Date currentDate = new Date();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.YEAR,year);
        calendarStart.set(Calendar.MONTH,0);
        calendarStart.set(Calendar.DAY_OF_MONTH,1);
        Date startDate = calendarStart.getTime();

        return new TimeInterval(startDate, currentDate);
    }

    public static String timeDifference(Date oldDate) {

        Date nowDate = new Date();

        long diff = nowDate.getTime() - oldDate.getTime();

        if (diff / 1000 < 1) {
            return String.format("%s %s", 1, "second");
        }

        double rez = diff / 1000;

        if (rez / 60 < 1) {
            int roundRez = (int)Math.ceil(rez);
            String timeString = roundRez == 1 ? "second" : "seconds";
            return String.format("%s %s", roundRez, timeString);
        }
        rez = rez / 60;

        if (rez / 60 < 1) {
            int roundRez = (int)Math.ceil(rez);
            String timeString = roundRez == 1 ? "minute" : "minutes";
            return String.format("%s %s", roundRez, timeString);
        }

        rez = rez / 60;

        if (rez / 24 < 1) {
            int roundRez = (int)Math.ceil(rez);
            String timeString = roundRez == 1 ? "hour" : "hours";
            return String.format("%s %s", roundRez, timeString);
        }

        rez = rez / 24;
        if (rez / 24 < 1) {
            int roundRez = (int)Math.ceil(rez);
            String timeString = roundRez == 1 ? "day" : "days";
            return String.format("%s %s", roundRez, timeString);
        }

        return "";
    }
}
