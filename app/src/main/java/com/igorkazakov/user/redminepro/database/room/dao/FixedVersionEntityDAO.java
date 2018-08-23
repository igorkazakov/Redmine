package com.igorkazakov.user.redminepro.database.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.igorkazakov.user.redminepro.database.room.entity.FixedVersionEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class FixedVersionEntityDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<FixedVersionEntity> fixedVersionEntities);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(List<FixedVersionEntity> fixedVersionEntities);

    @Transaction
    public void insertOrUpdate(List<FixedVersionEntity> fixedVersionEntities) {
        insert(fixedVersionEntities);
        update(fixedVersionEntities);
    }

    @Delete
    public abstract void delete(FixedVersionEntity fixedVersionEntities);

    @Query("SELECT * FROM FixedVersionEntity")
    public abstract Single<List<FixedVersionEntity>> getAll();

    @Query("SELECT * FROM FixedVersionEntity WHERE id = :id")
    public abstract FixedVersionEntity getFixedVersionById(Long id);
}
