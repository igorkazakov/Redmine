package com.igorkazakov.user.redminepro.database.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.igorkazakov.user.redminepro.database.room.entity.PriorityEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class PriorityEntityDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<PriorityEntity> priorityEntities);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(List<PriorityEntity> priorityEntities);

    @Transaction
    public void insertOrUpdate(List<PriorityEntity> priorityEntities) {
        insert(priorityEntities);
        update(priorityEntities);
    }

    @Delete
    public abstract void delete(PriorityEntity priorityEntity);

    @Query("SELECT * FROM PriorityEntity")
    public abstract Single<List<PriorityEntity>> getAll();

    @Query("SELECT * FROM PriorityEntity WHERE id = :id")
    public abstract Single<PriorityEntity> getPriorityById(Long id);
}
