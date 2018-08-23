package com.igorkazakov.user.redminepro.database.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects.TimeEntryCustomField;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = TimeEntryEntity.class,
        parentColumns = "id",
        childColumns = "timeEntryId",
        onDelete = CASCADE))

public class TimeEntryCustomFieldEntity extends EmptyEntity {

    @PrimaryKey
    private Integer id;
    private String customFieldName;
    private String value;
    private Integer timeEntryId;

    public TimeEntryCustomFieldEntity() {

    }

    public TimeEntryCustomFieldEntity(TimeEntryCustomField customField, Integer parentId) {
        this.id = customField.getId();
        this.customFieldName = customField.getName();
        this.value = customField.getValue();
        this.timeEntryId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public String getCustomFieldName() {
        return customFieldName;
    }

    public String getValue() {
        return value;
    }

    public Integer getTimeEntryId() {
        return timeEntryId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCustomFieldName(String customFieldName) {
        this.customFieldName = customFieldName;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTimeEntryId(Integer timeEntryId) {
        this.timeEntryId = timeEntryId;
    }

    public static TimeEntryCustomFieldEntity createEmptyInstance() {
        TimeEntryCustomFieldEntity entity = new TimeEntryCustomFieldEntity();
        entity.setEmpty(true);
        return entity;
    }
}
