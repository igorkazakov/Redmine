package com.igorkazakov.user.redminepro.database.room.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.igorkazakov.user.redminepro.database.room.dao.OggyCalendarDayDAO;
import com.igorkazakov.user.redminepro.database.room.entity.OggyCalendarDayEntity;

@Database(entities = {OggyCalendarDayEntity.class}, version = 1)
public abstract class RoomDbHelper extends RoomDatabase {

    public abstract OggyCalendarDayDAO oggyCalendarDayEntityDAO();
}
