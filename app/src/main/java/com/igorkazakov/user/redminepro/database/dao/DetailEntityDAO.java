package com.igorkazakov.user.redminepro.database.dao;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Detail;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.entity.DetailEntity;
import com.igorkazakov.user.redminepro.database.entity.JournalEntity;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 31.07.17.
 */

public class DetailEntityDAO extends BaseDaoImpl<DetailEntity, Long> {

    public DetailEntityDAO(ConnectionSource connectionSource, Class<DetailEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<DetailEntity> getAll() {
        List<DetailEntity> entityList = new ArrayList();
        try {
            entityList = this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return entityList;
        }
    }

    public void saveDetailEntity(DetailEntity entity) {

        if (entity == null) {
            return;
        }

        try {

            createOrUpdate(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveDetailEntities(List<DetailEntity> entityList) {

        if (entityList == null) {
            return;
        }

        for (DetailEntity item : entityList) {
            try {
                this.createOrUpdate(item);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteExtraEntitiesFromBd(List<Detail> detailList) {

        Set<String> nameSet = new HashSet<>();
        for (Detail detail: detailList) {
            if (detail.getName() != null) {
                nameSet.add(detail.getName());
            }
        }

        Set<String> newValueSet = new HashSet<>();
        for (Detail detail: detailList) {
            if (detail.getNewValue() != null) {
                newValueSet.add(detail.getNewValue());
            }
        }

        Set<String> oldValueSet = new HashSet<>();
        for (Detail detail: detailList) {
            if (detail.getOldValue() != null) {
                oldValueSet.add(detail.getOldValue());
            }
        }

        try {
            List<DetailEntity> detailEntityList = this.queryBuilder().where()
                    .not().in("name", nameSet).and()
                    .not().in("new_value", newValueSet).and()
                    .not().in("old_value", oldValueSet).query();

            delete(detailEntityList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllDetails(long parentId) {

        try {
            JournalEntity entity = DatabaseManager.getDatabaseHelper().getJournalEntityDAO().queryForId(parentId);
            if (entity != null) {
                delete(entity.getDetails());
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}