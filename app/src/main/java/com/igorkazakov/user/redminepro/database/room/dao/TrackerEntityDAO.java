package com.igorkazakov.user.redminepro.database.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.igorkazakov.user.redminepro.database.room.entity.TrackerEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class TrackerEntityDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<TrackerEntity> trackerEntities);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(List<TrackerEntity> trackerEntities);

    @Transaction
    public void insertOrUpdate(List<TrackerEntity> trackerEntities) {
        insert(trackerEntities);
        update(trackerEntities);
    }

    @Delete
    public abstract void delete(TrackerEntity trackerEntities);

    @Query("SELECT * FROM TrackerEntity")
    public abstract Single<List<TrackerEntity>> getAll();

    @Query("SELECT * FROM TrackerEntity WHERE id = :id")
    public abstract TrackerEntity getTrackerById(Long id);
}
