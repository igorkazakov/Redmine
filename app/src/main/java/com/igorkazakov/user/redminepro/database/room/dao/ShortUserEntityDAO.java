package com.igorkazakov.user.redminepro.database.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.igorkazakov.user.redminepro.database.room.entity.ShortUserEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class ShortUserEntityDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<ShortUserEntity> shortUserEntities);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(List<ShortUserEntity> shortUserEntities);

    @Transaction
    public void insertOrUpdate(List<ShortUserEntity> shortUserEntities) {
        insert(shortUserEntities);
        update(shortUserEntities);
    }

    @Delete
    public abstract void delete(ShortUserEntity shortUserEntities);

    @Query("SELECT * FROM ShortUserEntity")
    public abstract Single<List<ShortUserEntity>> getAll();

    @Query("SELECT * FROM ShortUserEntity WHERE id = :id")
    public abstract ShortUserEntity getShortUserById(Long id);
}
