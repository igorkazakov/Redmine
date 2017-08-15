package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.FixedVersion;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 14.08.17.
 */

@DatabaseTable(tableName = "VersionEntity")
public class VersionEntity {

    @DatabaseField(id = true)
    private Long id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "project_id")
    private long projectId;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

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

    public static VersionEntity convertItem(FixedVersion fixedVersion) {

        if (fixedVersion == null) {
            return null;
        }

        VersionEntity versionEntity = new VersionEntity();
        versionEntity.setId(fixedVersion.getId());

        if (fixedVersion.getProject() != null) {
            versionEntity.setProjectId(fixedVersion.getProject().getId());
        }

        versionEntity.setName(fixedVersion.getName());

        return versionEntity;
    }

    public static List<VersionEntity> convertItems(List<FixedVersion> items) {

        if (items == null) {
            return null;
        }

        ArrayList<VersionEntity> versionEntities = new ArrayList<>();
        for (FixedVersion item : items) {

            versionEntities.add(VersionEntity.convertItem(item));
        }

        return versionEntities;
    }
}