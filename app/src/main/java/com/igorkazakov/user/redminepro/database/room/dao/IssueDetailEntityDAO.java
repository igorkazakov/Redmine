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
    public abstract void insert(IssueDetailEntity issueDetailEntity);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(IssueDetailEntity issueDetailEntity);

    @Transaction
    public void insertOrUpdate(IssueDetailEntity issueDetailEntity) {
        insert(issueDetailEntity);
        update(issueDetailEntity);
    }

    @Delete
    public abstract void delete(IssueDetailEntity issueDetailEntities);

    @Query("SELECT * FROM IssueDetailEntity")
    public abstract Single<List<IssueDetailEntity>> getAll();

    @Query("SELECT * FROM IssueDetailEntity WHERE id = :id")
    public abstract Single<IssueDetailEntity> getIssueDetailById(Long id);
}
