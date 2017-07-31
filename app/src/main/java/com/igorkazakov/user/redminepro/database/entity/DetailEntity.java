package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Detail;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by user on 31.07.17.
 */
@DatabaseTable(tableName = "DetailEntity")
public class DetailEntity {

    @DatabaseField(columnName = "property")
    private String property;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "new_value")
    private String newValue;

    @DatabaseField(columnName = "old_value")
    private String oldValue;

    @DatabaseField(foreign = true, foreignAutoRefresh= true)
    protected JournalEntity parent;

    public void setParent(JournalEntity parent) {

        this.parent = parent;
    }

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

    public static ForeignCollection<DetailEntity> convertItems(List<Detail> detailList, JournalEntity parent) {

        if (detailList == null) {
            return null;
        }

        ForeignCollection<DetailEntity> detailEntityCollection = null;
        try {
            detailEntityCollection = DatabaseManager.getDatabaseHelper().getJournalEntityDAO().getEmptyForeignCollection("details");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Collection<DetailEntity> detailEntityCollection = new ArrayList<>();
        for (Detail detail: detailList) {
            DetailEntity detailEntity = new DetailEntity();
            detailEntity.setParent(parent);
            detailEntity.setName(detail.getName());
            detailEntity.setNewValue(detail.getNewValue());
            detailEntity.setOldValue(detail.getOldValue());
            detailEntity.setProperty(detail.getProperty());

            if (detailEntityCollection != null) {
                detailEntityCollection.add(detailEntity);
            }
        }

        return detailEntityCollection;
    }
}
