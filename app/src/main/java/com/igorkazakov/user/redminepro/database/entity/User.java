package com.igorkazakov.user.redminepro.database.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by user on 11.07.17.
 */

@DatabaseTable(tableName = "user")
public class User {

    @DatabaseField(id = true)
    private long userId;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "email")
    private String email;

    public User() {
        super();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public static class UserConverter {
//
//        public static List<User> convertItems(List<Item1> items) {
//
//
//        }
//    }
}
