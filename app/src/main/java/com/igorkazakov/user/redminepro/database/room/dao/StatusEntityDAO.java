package com.igorkazakov.user.redminepro.database.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.igorkazakov.user.redminepro.database.room.entity.StatusEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class StatusEntityDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<StatusEntity> statusEntities);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(List<StatusEntity> statusEntities);

    @Transaction
    public void insertOrUpdate(List<StatusEntity> statusEntities) {
        insert(statusEntities);
        update(statusEntities);
    }

    @Delete
    public abstract void delete(StatusEntity statusEntities);

    @Query("SELECT * FROM StatusEntity")
    public abstract Single<List<StatusEntity>> getAll();

    @Query("SELECT * FROM StatusEntity WHERE id = :id")
    public abstract StatusEntity getStatusById(Long id);
}
