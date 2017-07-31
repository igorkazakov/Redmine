package com.igorkazakov.user.redminepro.database.dao;

import com.igorkazakov.user.redminepro.database.entity.AttachmentEntity;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 31.07.17.
 */

public class AttachmentEntityDAO extends BaseDaoImpl<AttachmentEntity, Long> {

    public AttachmentEntityDAO(ConnectionSource connectionSource, Class<AttachmentEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<AttachmentEntity> getAll() {
        List<AttachmentEntity> entityList = new ArrayList();
        try {
            entityList = this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return entityList;
        }
    }

    public void saveAttachmentEntity(AttachmentEntity entity) {

        if (entity == null) {
            return;
        }

        try {

            createOrUpdate(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveAttachmentEntities(List<AttachmentEntity> entityList) {

        if (entityList == null) {
            return;
        }

        for (AttachmentEntity item : entityList) {
            try {
                this.createOrUpdate(item);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}