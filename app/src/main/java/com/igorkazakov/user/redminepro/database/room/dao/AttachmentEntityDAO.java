package com.igorkazakov.user.redminepro.database.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.igorkazakov.user.redminepro.database.room.entity.AttachmentEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class AttachmentEntityDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<AttachmentEntity> attachmentEntities);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(List<AttachmentEntity> attachmentEntities);

    @Transaction
    public void insertOrUpdate(List<AttachmentEntity> attachmentEntities) {
        insert(attachmentEntities);
        update(attachmentEntities);
    }

    @Delete
    public abstract void delete(AttachmentEntity attachmentEntities);

    @Query("SELECT * FROM AttachmentEntity")
    public abstract Single<List<AttachmentEntity>> getAll();

    @Query("SELECT * FROM AttachmentEntity WHERE parentId = :id")
    public abstract Single<List<AttachmentEntity>> getAttachmentsById(Long id);
}
