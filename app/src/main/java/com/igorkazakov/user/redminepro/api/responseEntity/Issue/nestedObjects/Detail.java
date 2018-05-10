package com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by user on 28.07.17.
 */

public class Detail extends RealmObject {

    @PrimaryKey
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
}
