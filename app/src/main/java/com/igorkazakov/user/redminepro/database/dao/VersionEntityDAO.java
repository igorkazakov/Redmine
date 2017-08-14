package com.igorkazakov.user.redminepro.database.dao;

import com.igorkazakov.user.redminepro.database.entity.VersionEntity;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 14.08.17.
 */

public class VersionEntityDAO extends BaseDaoImpl<VersionEntity, Long> {

    public VersionEntityDAO(ConnectionSource connectionSource, Class<VersionEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<VersionEntity> getAll() {
        List<VersionEntity> versionEntities = new ArrayList();
        try {
            versionEntities = this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return versionEntities;
        }
    }

    public void saveVersionEntity(VersionEntity versionEntity) {

        if (versionEntity == null) {
            return;
        }

        try {

            createOrUpdate(versionEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveVersionEntities(List<VersionEntity> versionEntities) {

        if (versionEntities == null) {
            return;
        }

        for (VersionEntity versionEntity : versionEntities) {
            try {
                this.createOrUpdate(versionEntity);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}