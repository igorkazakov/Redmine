package com.igorkazakov.user.redminepro.database.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.igorkazakov.user.redminepro.database.room.entity.IssueEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class IssueEntityDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<IssueEntity> issueEntities);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(List<IssueEntity> issueEntities);

    @Transaction
    public void insertOrUpdate(List<IssueEntity> issueEntities) {
        insert(issueEntities);
        update(issueEntities);
    }

    @Delete
    public abstract void delete(IssueEntity issueEntities);

    @Query("SELECT * FROM IssueEntity")
    public abstract Single<List<IssueEntity>> getAll();

    @Query("SELECT * FROM IssueEntity WHERE id IN (:childrenIds)")
    public abstract Single<List<IssueEntity>> getChildIssues(List<Long> childrenIds);
}
