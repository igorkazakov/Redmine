package com.igorkazakov.user.redminepro.database.room.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Namable;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortProject;

@Entity(tableName = "FixedVersionEntity")
public class FixedVersionEntity implements Namable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("project")
    @Expose
    @Embedded
    private ShortProject project;

    public ShortProject getProject() {
        return project;
    }

    public void setProject(ShortProject project) {
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
