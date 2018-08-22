package com.igorkazakov.user.redminepro.database.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.igorkazakov.user.redminepro.database.room.converters.DateConverter;
import com.igorkazakov.user.redminepro.database.room.entity.TimeEntryEntity;
import com.igorkazakov.user.redminepro.models.TimeInterval;
import com.igorkazakov.user.redminepro.models.TimeModel;

import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@Dao
@TypeConverters({DateConverter.class})
public abstract class TimeEntryEntityDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<TimeEntryEntity> timeEntries);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(List<TimeEntryEntity> timeEntries);

    @Transaction
    public void insertOrUpdate(List<TimeEntryEntity> timeEntries) {
        insert(timeEntries);
        update(timeEntries);
    }

    @Delete
    public abstract void delete(TimeEntryEntity timeEntryEntity);

    @Query("SELECT * FROM TimeEntryEntity")
    public abstract Single<List<TimeEntryEntity>> getAll();

    @Query("SELECT sum(hours) FROM TimeEntryEntity WHERE spentOn >= :start AND spentOn <= :end AND type = :timeType")
    public abstract Single<Float> getWorkHoursWithIntervalAndTimeType(Long start, Long end, String timeType);

    @Query("SELECT sum(hours) FROM TimeEntryEntity WHERE spentOn = :date AND type = :timeType")
    public abstract Single<Float> getWorkHoursWithDateAndTimeType(Long date, String timeType);

    public Single<TimeModel> getWorkHoursWithInterval(TimeInterval interval) {

        Single<Float> regular = getWorkHoursWithIntervalAndTimeType(
                interval.getStartLong(),
                interval.getEndLong(),
                TimeType.REGULAR.getValue())
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(0f);

        Single<Float> fuckup = getWorkHoursWithIntervalAndTimeType(
                interval.getStartLong(),
                interval.getEndLong(),
                TimeType.FUCKUP.getValue())
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(0f);

        Single<Float> teamFuckup = getWorkHoursWithIntervalAndTimeType(
                interval.getStartLong(),
                interval.getEndLong(),
                TimeType.TEAMFUCKUP.getValue())
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(0f);

        return Single.zip(regular,
                fuckup,
                teamFuckup,
                (regularResult, fuckupResult, teamFuckupResult) -> {

                    TimeModel model = new TimeModel(0, 0, 0);

                    model.setRegularTime(regularResult);
                    model.setFuckupTime(fuckupResult);
                    model.setTeamFuckupTime(teamFuckupResult);

                    return model;

                }).subscribeOn(Schedulers.io());
    }

    public Single<TimeModel> getWorkHoursWithDate(Date date) {

        Single<Float> regular = getWorkHoursWithDateAndTimeType(
                date.getTime(),
                TimeType.REGULAR.getValue())
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(0f);

        Single<Float> fuckup = getWorkHoursWithDateAndTimeType(
                date.getTime(),
                TimeType.FUCKUP.getValue())
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(0f);

        Single<Float> teamFuckup = getWorkHoursWithDateAndTimeType(
                date.getTime(),
                TimeType.TEAMFUCKUP.getValue())
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(0f);

        return Single.zip(regular,
                fuckup,
                teamFuckup,
                (regularResult, fuckupResult, teamFuckupResult) -> {

                    TimeModel model = new TimeModel(0, 0, 0);

                    model.setRegularTime(regularResult);
                    model.setFuckupTime(fuckupResult);
                    model.setTeamFuckupTime(teamFuckupResult);

                    return model;

                }).subscribeOn(Schedulers.io());
    }

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
}
