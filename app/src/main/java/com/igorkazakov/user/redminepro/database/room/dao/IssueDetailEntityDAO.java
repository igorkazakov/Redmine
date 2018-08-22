package com.igorkazakov.user.redminepro.database.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.igorkazakov.user.redminepro.database.room.entity.IssueDetailEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class IssueDetailEntityDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<IssueDetailEntity> issueDetailEntities);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(List<IssueDetailEntity> issueDetailEntities);

    @Transaction
    public void insertOrUpdate(List<IssueDetailEntity> issueDetailEntities) {
        insert(issueDetailEntities);
        update(issueDetailEntities);
    }

    @Delete
    public abstract void delete(IssueDetailEntity issueDetailEntities);

    @Query("SELECT * FROM IssueDetailEntity")
    public abstract Single<List<IssueDetailEntity>> getAll();

    @Query("SELECT * FROM IssueDetailEntity WHERE id = :id")
    public abstract Single<IssueDetailEntity> getIssueDetailById(Long id);
}
