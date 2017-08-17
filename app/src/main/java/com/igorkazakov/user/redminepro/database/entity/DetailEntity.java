package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Detail;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.dao.DetailEntityDAO;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by user on 31.07.17.
 */
@DatabaseTable(tableName = "DetailEntity")
public class DetailEntity {

    @DatabaseField(id = true)
    private long id;

    @DatabaseField(columnName = "property")
    private String property;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "new_value")
    private String newValue;

    @DatabaseField(columnName = "old_value")
    private String oldValue;

    @DatabaseField(columnName = "parent_id")
    protected long parent;

    public void setParent(long parent) {

        this.parent = parent;
    }

    public long getParent() {
        return parent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public static void convertItems(List<Detail> detailList, JournalEntity parent) {

        if (detailList == null) {
            return;
        }

        DetailEntityDAO detailEntityDAO = DatabaseManager.getDatabaseHelper().getDetailEntityDAO();
        detailEntityDAO.deleteDetailsByParent(parent.getId());
        try {

            int idOffset = 0;
            for (Detail detail : detailList) {
                DetailEntity detailEntity = new DetailEntity();
                detailEntity.setId(parent.getId() + ++idOffset);
                detailEntity.setParent(parent.getId());
                detailEntity.setName(detail.getName());
                detailEntity.setNewValue(detail.getNewValue());
                detailEntity.setOldValue(detail.getOldValue());
                detailEntity.setProperty(detail.getProperty());

                detailEntityDAO.createOrUpdate(detailEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
