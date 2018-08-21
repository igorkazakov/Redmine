package com.igorkazakov.user.redminepro.database.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects.TimeEntryCustomField;

import io.realm.annotations.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = TimeEntryEntity.class,
        parentColumns = "id",
        childColumns = "timeEntryId",
        onDelete = CASCADE))

public class TimeEntryCustomFieldEntity {

    @PrimaryKey
    private Integer id;
    private String name;
    private String value;
    private Integer timeEntryId;

    TimeEntryCustomFieldEntity(TimeEntryCustomField customField, Integer parentId) {
        this.id = customField.getId();
        this.name = customField.getName();
        this.value = customField.getValue();
        this.timeEntryId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Integer getTimeEntryId() {
        return timeEntryId;
    }
}
