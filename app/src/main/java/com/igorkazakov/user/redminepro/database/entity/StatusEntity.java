package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 09.08.17.
 */
@DatabaseTable(tableName = "StatusEntity")
public class StatusEntity {

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

    public static StatusEntity convertItem(Status status) {

        if (status == null) {
            return null;
        }

        StatusEntity statusEntity = new StatusEntity();
        statusEntity.setId(status.getId());
        statusEntity.setName(status.getName());

        return statusEntity;
    }

    public static List<StatusEntity> convertItems(List<Status> items) {

        if (items == null) {
            return null;
        }

        ArrayList<StatusEntity> statusEntities = new ArrayList<>();
        for (Status item : items) {

            statusEntities.add(StatusEntity.convertItem(item));
        }

        return statusEntities;
    }
}
