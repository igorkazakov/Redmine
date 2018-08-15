package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.CalendarDay.OggyCalendarDay;
import com.igorkazakov.user.redminepro.models.TimeInterval;

import java.util.Date;
import java.util.List;

import io.realm.Realm;

public class CalendarDayDAO {

    public static void saveCalendarDays(List<OggyCalendarDay> calendarDays) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(calendarDays);
        realm.commitTransaction();
    }

    public static long getHoursNormForInterval(TimeInterval interval) {

        return Realm.getDefaultInstance()
                .where(OggyCalendarDay.class)
                .greaterThanOrEqualTo("date", interval.getStart())
                .and()
                .lessThanOrEqualTo("date", interval.getEnd())
                .findAll()
                .sum("hours")
                .longValue();
    }

    public static long getHoursNormForDate(Date date) {

        return Realm.getDefaultInstance()
                .where(OggyCalendarDay.class)
                .equalTo("date", date)
                .findAll()
                .sum("hours")
                .longValue();
    }

    public static OggyCalendarDay getCalendarDayWithDate(Date date) {

        return Realm.getDefaultInstance()
                .where(OggyCalendarDay.class)
                .equalTo("date", date)
                .findFirst();

    }

    public static List<OggyCalendarDay> getCalendarMonthDaysWithDate(TimeInterval timeInterval) {

        return Realm.getDefaultInstance()
                .where(OggyCalendarDay.class)
                .greaterThanOrEqualTo("date", timeInterval.getStart())
                .and()
                .lessThanOrEqualTo("date", timeInterval.getEnd())
                .findAll();
    }
}
