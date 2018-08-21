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

import java.util.List;

import io.reactivex.Single;

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
    public abstract List<TimeEntryEntity> getAll();

    @Query("SELECT sum(hours) FROM TimeEntryEntity WHERE spentOn >= :start AND spentOn <= :end AND ")
    public abstract float getWorkHoursWithIntervalAndTimeType(Long start, Long end, String timeType);




    @Query("SELECT sum(hours) FROM OggyCalendarDayEntity WHERE date >= :start AND date <= :end")
    public abstract Single<Long> getHoursNormForInterval(Long start, Long end);


    @Query("SELECT sum(hours) FROM OggyCalendarDayEntity WHERE date = :date")
    public abstract Single<Long> getHoursNormForDate(Long date);


}
