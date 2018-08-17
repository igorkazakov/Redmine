package com.igorkazakov.user.redminepro.database.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.igorkazakov.user.redminepro.database.room.entity.OggyCalendarDayEntity;

import java.util.List;

@Dao
public interface OggyCalendarDayDAO {

    @Query("SELECT * FROM OggyCalendarDayEntity")
    List<OggyCalendarDayEntity> getAll();

    @Query("SELECT * FROM OggyCalendarDayEntity WHERE id = :id")
    OggyCalendarDayEntity getById(long id);

    @Query("SELECT sum(hours) FROM OggyCalendarDayEntity WHERE date >= date(:start) AND date <= date(:end)")
    long getHoursNormForInterval(long start, long end);

    @Query("SELECT sum(hours) FROM OggyCalendarDayEntity WHERE date = date(:date)")
    long getHoursNormForDate(long date);

    @Insert
    void insert(List<OggyCalendarDayEntity> calendarDays);

    @Update
    void update(OggyCalendarDayEntity oggyCalendarDayEntity);

    @Delete
    void delete(OggyCalendarDayEntity oggyCalendarDayEntity);
}
