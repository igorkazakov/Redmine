package com.igorkazakov.user.redminepro.database.dao;

import com.igorkazakov.user.redminepro.database.entity.PriorityEntity;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 14.08.17.
 */

public class PriorityEntityDAO extends BaseDaoImpl<PriorityEntity, Long> {

    public PriorityEntityDAO(ConnectionSource connectionSource, Class<PriorityEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<PriorityEntity> getAll() {
        List<PriorityEntity> priorityEntities = new ArrayList();
        try {
            priorityEntities = this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return priorityEntities;
        }
    }

    public void savePriorityEntity(PriorityEntity priorityEntity) {

        if (priorityEntity == null) {
            return;
        }

        try {

            createOrUpdate(priorityEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void savePriorityEntities(List<PriorityEntity> priorityEntities) {

        if (priorityEntities == null) {
            return;
        }

        for (PriorityEntity priorityEntity : priorityEntities) {
            try {
                this.createOrUpdate(priorityEntity);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}