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
import com.igorkazakov.user.redminepro.database.room.entity.OggyCalendarDayEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class OggyCalendarDayDAO {

    @Query("SELECT * FROM OggyCalendarDayEntity")
    public abstract Single<List<OggyCalendarDayEntity>> getAll();

    @Query("SELECT * FROM OggyCalendarDayEntity WHERE id = :id")
    public abstract Single<OggyCalendarDayEntity> getById(long id);

    @TypeConverters({DateConverter.class})
    @Query("SELECT sum(hours) FROM OggyCalendarDayEntity WHERE date >= :start AND date <= :end")
    public abstract Single<Long> getHoursNormForInterval(Long start, Long end);

    @TypeConverters({DateConverter.class})
    @Query("SELECT sum(hours) FROM OggyCalendarDayEntity WHERE date = :date")
    public abstract Single<Long> getHoursNormForDate(Long date);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<OggyCalendarDayEntity> calendarDays);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(List<OggyCalendarDayEntity> calendarDays);

    @Transaction
    public void insertOrUpdate(List<OggyCalendarDayEntity> calendarDays) {
        insert(calendarDays);
        update(calendarDays);
    }

    @Delete
    public abstract void delete(OggyCalendarDayEntity oggyCalendarDayEntity);
}
