package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.TimeEntry;
import com.igorkazakov.user.redminepro.models.TimeInterval;
import com.igorkazakov.user.redminepro.models.TimeModel;

import java.util.Date;
import java.util.List;

import io.realm.Realm;

public class TimeEntryDAO {

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

        for (TimeEntry timeEntry: timeEntries) {
            if (timeEntry.getCustomFields().size() > 0) {
                String type = timeEntry.getCustomFields().get(0).getValue();
                timeEntry.setType(type != null ? type : "");
            }
        }

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.copyToRealmOrUpdate(timeEntries);
        realm.commitTransaction();
    }

    public static List<TimeEntry> getAll() {

        return Realm.getDefaultInstance()
                .where(TimeEntry.class)
                .findAll();
    }

    private static float getWorkHoursWithIntervalAndTimeType(TimeInterval interval, TimeType timeType) {

        return Realm.getDefaultInstance()
                .where(TimeEntry.class)
                .greaterThanOrEqualTo("spentOn", interval.getStart())
                .and()
                .lessThanOrEqualTo("spentOn", interval.getEnd())
                .and()
                .equalTo("type", timeType.getValue())
                .findAll()
                .sum("hours")
                .floatValue();
    }

    private static float getWorkHoursWithDateAndTimeType(Date date, TimeType timeType) {

        return Realm.getDefaultInstance()
                .where(TimeEntry.class)
                .equalTo("spentOn", date)
                .and()
                .equalTo("type", timeType.getValue())
                .findAll()
                .sum("hours")
                .floatValue();
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
