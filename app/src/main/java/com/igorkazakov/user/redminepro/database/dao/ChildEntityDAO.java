package com.igorkazakov.user.redminepro.database.dao;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Child;
import com.igorkazakov.user.redminepro.database.entity.ChildEntity;
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

public class ChildEntityDAO extends BaseDaoImpl<ChildEntity, Long> {

    public ChildEntityDAO(ConnectionSource connectionSource, Class<ChildEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<ChildEntity> getAll() {
        List<ChildEntity> entityList = new ArrayList();
        try {
            entityList = this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return entityList;
        }
    }

    public void saveIssueEntity(ChildEntity entity) {

        if (entity == null) {
            return;
        }

        try {

            createOrUpdate(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveChildEntities(List<ChildEntity> entityList) {

        if (entityList == null) {
            return;
        }

        for (ChildEntity item : entityList) {
            try {
                this.createOrUpdate(item);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteExtraEntitiesFromBd(List<Child> childList) {

        Set<Long> set = new HashSet<>();
        for (Child child: childList) {
            set.add(child.getId());
        }

        try {
            List<ChildEntity> childEntityList = this.queryBuilder().where().not().in("id", set).query();
            delete(childEntityList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}