package com.igorkazakov.user.redminepro.database.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.igorkazakov.user.redminepro.database.room.entity.ProjectEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class ProjectEntityDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<ProjectEntity> projectEntities);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(List<ProjectEntity> projectEntities);

    @Transaction
    public void insertOrUpdate(List<ProjectEntity> projectEntities) {
        insert(projectEntities);
        update(projectEntities);
    }

    @Delete
    public abstract void delete(ProjectEntity projectEntities);

    @Query("SELECT * FROM ProjectEntity")
    public abstract Single<List<ProjectEntity>> getAll();
}
