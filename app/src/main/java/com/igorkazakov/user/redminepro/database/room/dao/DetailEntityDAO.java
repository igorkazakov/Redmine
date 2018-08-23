package com.igorkazakov.user.redminepro.database.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.igorkazakov.user.redminepro.database.room.entity.DetailEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class DetailEntityDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<DetailEntity> detailEntities);

    @Transaction
    public void deleteAllAndInsert(List<DetailEntity> detailEntities) {
        deleteAll();
        insert(detailEntities);
    }

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(List<DetailEntity> detailEntities);

    @Transaction
    public void insertOrUpdate(List<DetailEntity> detailEntities) {
        insert(detailEntities);
        update(detailEntities);
    }

    @Delete
    public abstract void delete(DetailEntity detailEntities);

    @Query("SELECT * FROM DetailEntity")
    public abstract Single<List<DetailEntity>> getAll();

    @Query("DELETE FROM DetailEntity")
    public abstract void deleteAll();

    @Query("SELECT * FROM DetailEntity WHERE parentId = :id")
    public abstract Single<List<DetailEntity>> getDetailsById(Long id);
}
