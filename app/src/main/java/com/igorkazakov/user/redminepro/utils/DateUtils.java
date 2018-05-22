package com.igorkazakov.user.redminepro.utils;

import com.igorkazakov.user.redminepro.models.TimeInterval;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

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

        Date date = new Date();

        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String stringFromDate(Date date, SimpleDateFormat format) {

        return format.format(date);
    }

    public static Date getYesterday(Calendar calendar) {

        removeTime(calendar);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static Date getYesterday() {

        return getYesterday(Calendar.getInstance());
    }

    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public static Date getMonday(Date date) {

        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        removeTime(c);
        int today = c.get(Calendar.DAY_OF_WEEK);
        c.add(Calendar.DAY_OF_WEEK, -today + Calendar.MONDAY);
        return c.getTime();
    }

    public static TimeInterval getPreviousWeekInterval(Date date) {

        int daysForPreviousSunday = -1;
        int daysForPreviousMonday = -6;

        Calendar c = Calendar.getInstance();
        c.setTime(DateUtils.getMonday(date));
        removeTime(c);
        c.add(Calendar.DATE, daysForPreviousSunday);
        Date end = c.getTime();
        c.add(Calendar.DATE, daysForPreviousMonday);
        Date start = c.getTime();

        return new TimeInterval(start, end);
    }

    public static TimeInterval getPreviousWeekInterval() {

        return getPreviousWeekInterval(new Date());
    }

    public static TimeInterval getCurrentWeekInterval(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(DateUtils.getMonday(date));
        Date start = c.getTime();
        Date end = getDateWithoutTime(date);

        return new TimeInterval(start, end);
    }

    public static TimeInterval getCurrentWeekInterval() {

        return getCurrentWeekInterval(new Date());
    }

    public static TimeInterval getCurrentMonthInterval(Date date) {

        Date start, end;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateWithoutTime(date));
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        start = calendar.getTime();

        end = getDateWithoutTime(date);
        return new TimeInterval(start, end);
    }

    public static TimeInterval getCurrentMonthInterval() {

        return getCurrentMonthInterval(new Date());
    }

    public static TimeInterval getCurrentWholeWeekInterval(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(DateUtils.getMonday(date));
        Date start = c.getTime();
        c.add(Calendar.DAY_OF_WEEK, 6);
        Date end = c.getTime();
        return new TimeInterval(start, end);
    }

    public static TimeInterval getCurrentWholeWeekInterval() {

        return getCurrentWholeWeekInterval(new Date());
    }

    public static TimeInterval getMonthInterval(Date date, int month) {

        Date start, end;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateWithoutTime(date));

        calendar.set(Calendar.MONTH, month);

        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        start = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        end = calendar.getTime();

        return new TimeInterval(start, end);
    }

    public static TimeInterval getMonthInterval(int month) {

        return getMonthInterval(new Date(), month);
    }

    public static TimeInterval getIntervalFromStartYear(Date date) {

        int year = Calendar.getInstance().get(Calendar.YEAR);
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.YEAR,year);
        calendarStart.set(Calendar.MONTH,0);
        calendarStart.set(Calendar.DAY_OF_MONTH,1);
        removeTime(calendarStart);
        Date startDate = calendarStart.getTime();

        return new TimeInterval(startDate, getDateWithoutTime(date));
    }

    public static TimeInterval getIntervalFromStartYear() {

        return getIntervalFromStartYear(new Date());
    }

    private static void removeTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static Date getDateWithoutTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        removeTime(cal);
        return cal.getTime();
    }

    public static String timeDifference(Date nowDate, Date oldDate) {

        long diff = nowDate.getTime() - oldDate.getTime();

        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(nowDate);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(oldDate);

        int diffYears = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int years = Math.abs(diffYears);
        int months = Math.abs(diffYears * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH));
        long days = Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        long minutes = Math.abs(TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS));
        long seconds = Math.abs(TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS));

        if (years > 0) {

            return String.format("%s %s", years, TextUtils.getPluralForm(years, "year"));

        } else if (months > 0) {

            return String.format("%s %s", months, TextUtils.getPluralForm(months, "month"));

        } else if (days > 0) {

            return String.format("%s %s", days, TextUtils.getPluralForm(days, "day"));

        } if (minutes > 0) {
            return String.format("%s %s", minutes, TextUtils.getPluralForm(minutes, "minute"));

        } if (seconds > 0) {
            return String.format("%s %s", seconds, TextUtils.getPluralForm(seconds, "second"));

        } else {
            return "";
        }
    }

    public static String timeDifference(Date oldDate) {

        return timeDifference(new Date(), oldDate);
    }
}
