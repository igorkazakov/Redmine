package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.TimeEntry;
import com.igorkazakov.user.redminepro.models.TimeInterval;
import com.igorkazakov.user.redminepro.models.TimeModel;

import java.util.Date;
import java.util.List;

import io.realm.Realm;

public class TimeEntryRealmDAO {

    public enum TimeType {

        REGULAR("Regular"),
        FUCKUP("Fuc%up"),
        TEAMFUCKUP("Duties");

        private String value;

        private TimeType(String string){
            this.value = string;
        }

        public String getValue(){
            return  this.value;
        }
    }

    public static void saveTimeEntries(List<TimeEntry> timeEntries) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(timeEntries);
        realm.commitTransaction();
    }

    public static List<TimeEntry> getAll() {

        return Realm.getDefaultInstance()
                .where(TimeEntry.class)
                .findAll();
    }

    private static long getWorkHoursWithIntervalAndTimeType(TimeInterval interval, TimeType timeType) {

        return Realm.getDefaultInstance()
                .where(TimeEntry.class)
                .greaterThanOrEqualTo("spent_on", interval.getStart())
                .and()
                .lessThanOrEqualTo("spent_on", interval.getEnd())
                .and()
                .equalTo("type", timeType.getValue())
                .findAll()
                .sum("hours")
                .longValue();
    }

    private static long getWorkHoursWithDateAndTimeType(Date date, TimeType timeType) {

        return Realm.getDefaultInstance()
                .where(TimeEntry.class)
                .equalTo("spent_on", date)
                .and()
                .equalTo("type", timeType.getValue())
                .findAll()
                .sum("hours")
                .longValue();
    }

    public static TimeModel getWorkHoursWithInterval(TimeInterval interval) {

        TimeModel model = new TimeModel(0, 0, 0);

        model.setRegularTime(getWorkHoursWithIntervalAndTimeType(interval, TimeType.REGULAR));
        model.setFuckupTime(getWorkHoursWithIntervalAndTimeType(interval, TimeType.FUCKUP));
        model.setTeamFuckupTime(getWorkHoursWithIntervalAndTimeType(interval, TimeType.TEAMFUCKUP));

        return model;
    }

    public static TimeModel getWorkHoursWithDate(Date date) {

        TimeModel model = new TimeModel(0, 0, 0);

        model.setRegularTime(getWorkHoursWithDateAndTimeType(date, TimeType.REGULAR));
        model.setFuckupTime(getWorkHoursWithDateAndTimeType(date, TimeType.FUCKUP));
        model.setTeamFuckupTime(getWorkHoursWithDateAndTimeType(date, TimeType.TEAMFUCKUP));

        return model;
    }
}
