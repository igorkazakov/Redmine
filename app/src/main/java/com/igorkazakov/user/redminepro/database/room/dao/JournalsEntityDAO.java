package com.igorkazakov.user.redminepro.database.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.igorkazakov.user.redminepro.database.room.entity.JournalsEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class JournalsEntityDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<JournalsEntity> journalsEntities);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(List<JournalsEntity> journalsEntities);

    @Transaction
    public void insertOrUpdate(List<JournalsEntity> journalsEntities) {
        insert(journalsEntities);
        update(journalsEntities);
    }

    @Delete
    public abstract void delete(JournalsEntity journalsEntities);

    @Query("SELECT * FROM JournalsEntity")
    public abstract Single<List<JournalsEntity>> getAll();

    @Query("SELECT * FROM JournalsEntity WHERE parentId = :id")
    public abstract Single<List<JournalsEntity>> getJournalsById(Long id);
}
