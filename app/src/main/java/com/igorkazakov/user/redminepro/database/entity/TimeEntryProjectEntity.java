package com.igorkazakov.user.redminepro.database.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TimeEntryProjectEntity")
public class TimeEntryProjectEntity {

    @SerializedName("project_id")
    @Expose
    @DatabaseField(id = true)
    private Integer id;

    @SerializedName("project_name")
    @Expose
    @DatabaseField(columnName = "name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
