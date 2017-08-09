package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortUser;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 09.08.17.
 */

@DatabaseTable(tableName = "UserEntity")
public class UserEntity {

    @DatabaseField(id = true)
    private Long id;

    @DatabaseField(columnName = "id")
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

    public static UserEntity convertItem(ShortUser user) {

        if (user == null) {
            return null;
        }

        UserEntity trackerEntity = new UserEntity();
        trackerEntity.setId(user.getId());
        trackerEntity.setName(user.getName());

        return trackerEntity;
    }

    public static List<UserEntity> convertItems(List<ShortUser> items) {

        if (items == null) {
            return null;
        }

        ArrayList<UserEntity> trackerEntities = new ArrayList<>();
        for (ShortUser item : items) {

            trackerEntities.add(UserEntity.convertItem(item));
        }

        return trackerEntities;
    }
}