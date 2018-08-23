package com.igorkazakov.user.redminepro.database.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Detail;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = JournalsEntity.class,
        parentColumns = "id",
        childColumns = "parentId",
        onDelete = CASCADE))

public class DetailEntity extends EmptyEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @SerializedName("property")
    @Expose
    private String property;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("new_value")
    @Expose
    private String newValue;
    @SerializedName("old_value")
    @Expose
    private String oldValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long parentId;

    public DetailEntity() {

    }

    public DetailEntity(Detail detail, long parentId) {
        this.property = detail.getProperty();
        this.name = detail.getName();
        this.newValue = detail.getNewValue();
        this.oldValue = detail.getOldValue();
        this.parentId = parentId;
        generateId();
    }

    private void generateId() {

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.id = new Date().getTime();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public static DetailEntity createEmptyInstance() {
        DetailEntity entity = new DetailEntity();
        entity.setEmpty(true);
        return entity;
    }
}
