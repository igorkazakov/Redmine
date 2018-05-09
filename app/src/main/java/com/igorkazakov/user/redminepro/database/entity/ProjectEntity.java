package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Project;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 09.08.17.
 */

@DatabaseTable(tableName = "ProjectEntity")
public class ProjectEntity {

    @DatabaseField(id = true)
    private Long id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "identifier")
    private String identifier;
    @DatabaseField(columnName = "description")
    private String description;
    @DatabaseField(columnName = "status")
    private String status;

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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static ProjectEntity convertItem(Project project) {

        if (project == null) {
            return null;
        }

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(project.getId());
        projectEntity.setName(project.getName());
        projectEntity.setDescription(project.getDescription());
        projectEntity.setIdentifier(project.getIdentifier());
        projectEntity.setStatus(project.getStatus());

        return projectEntity;
    }

    public static List<ProjectEntity> convertItems(List<Project> items) {

        if (items == null) {
            return null;
        }

        ArrayList<ProjectEntity> statusEntities = new ArrayList<>();
        for (Project item : items) {

            statusEntities.add(ProjectEntity.convertItem(item));
        }

        return statusEntities;
    }
}