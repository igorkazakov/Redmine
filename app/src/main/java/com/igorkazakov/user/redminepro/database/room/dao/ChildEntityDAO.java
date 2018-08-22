package com.igorkazakov.user.redminepro.database.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.igorkazakov.user.redminepro.database.room.entity.ChildEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class ChildEntityDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<ChildEntity> childEntities);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(List<ChildEntity> childEntities);

    @Transaction
    public void insertOrUpdate(List<ChildEntity> childEntities) {
        insert(childEntities);
        update(childEntities);
    }

    @Delete
    public abstract void delete(ChildEntity childEntities);

    @Query("SELECT * FROM ChildEntity")
    public abstract Single<List<ChildEntity>> getAll();

    @Query("SELECT * FROM ChildEntity WHERE parentId = :id")
    public abstract Single<List<ChildEntity>> getChildrensById(Long id);
}
