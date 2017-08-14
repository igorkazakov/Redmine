package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Tracker;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 09.08.17.
 */

@DatabaseTable(tableName = "TrackerEntity")
public class TrackerEntity {

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

    public static TrackerEntity convertItem(Tracker tracker) {

        if (tracker == null) {
            return null;
        }

        TrackerEntity trackerEntity = new TrackerEntity();
        trackerEntity.setId(tracker.getId());
        trackerEntity.setName(tracker.getName());

        return trackerEntity;
    }

    public static List<TrackerEntity> convertItems(List<Tracker> items) {

        if (items == null) {
            return null;
        }

        ArrayList<TrackerEntity> trackerEntities = new ArrayList<>();
        for (Tracker item : items) {

            trackerEntities.add(TrackerEntity.convertItem(item));
        }

        return trackerEntities;
    }
}