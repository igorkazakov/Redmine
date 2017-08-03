package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Detail;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.dao.DetailEntityDAO;
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

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    protected JournalEntity parent;

    public void setParent(JournalEntity parent) {

        this.parent = parent;
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

    public static ForeignCollection<DetailEntity> convertItems(List<Detail> detailList, JournalEntity parent) {

        if (detailList == null) {
            return null;
        }

        DetailEntityDAO detailEntityDAO = DatabaseManager.getDatabaseHelper().getDetailEntityDAO();
        detailEntityDAO.deleteAllDetails(parent.getId());

        ForeignCollection<DetailEntity> detailEntityCollection = parent.getDetails();
        try {

            if (detailEntityCollection == null) {
                detailEntityCollection = DatabaseManager.getDatabaseHelper().getJournalEntityDAO().getEmptyForeignCollection("details");
            }

            int idOffset = 0;
            for (Detail detail : detailList) {
                DetailEntity detailEntity = new DetailEntity();
                detailEntity.setId(parent.getId() + ++idOffset);
                detailEntity.setParent(parent);
                detailEntity.setName(detail.getName());
                detailEntity.setNewValue(detail.getNewValue());
                detailEntity.setOldValue(detail.getOldValue());
                detailEntity.setProperty(detail.getProperty());

                if (detailEntityCollection != null) {
                    detailEntityCollection.add(detailEntity);
                }

                detailEntityDAO.createOrUpdate(detailEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detailEntityCollection;
    }
}
