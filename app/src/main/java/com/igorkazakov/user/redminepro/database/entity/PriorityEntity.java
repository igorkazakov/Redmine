package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Priority;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by user on 14.08.17.
 */

@DatabaseTable(tableName = "PriorityEntity")
public class PriorityEntity {

    @DatabaseField(id = true)
    private Long id;

    @DatabaseField(columnName = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static PriorityEntity convertItem(Priority priority) {

        if (priority == null) {
            return null;
        }

        PriorityEntity versionEntity = new PriorityEntity();
        versionEntity.setId(priority.getId());
        versionEntity.setName(priority.getName());

        return versionEntity;
    }
}