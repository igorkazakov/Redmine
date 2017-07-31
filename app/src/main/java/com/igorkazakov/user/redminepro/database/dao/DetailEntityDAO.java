package com.igorkazakov.user.redminepro.database.dao;

import com.igorkazakov.user.redminepro.database.entity.DetailEntity;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}