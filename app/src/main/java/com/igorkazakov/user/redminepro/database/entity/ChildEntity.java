package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Child;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.dao.ChildEntityDAO;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by user on 31.07.17.
 */
@DatabaseTable(tableName = "ChildEntity")
public class ChildEntity {

    @DatabaseField(id = true)
    private Long id;

    @DatabaseField(columnName = "tracker_name")
    private String trackerName;

    @DatabaseField(columnName = "subject")
    private String subject;

    @DatabaseField(foreign = true, foreignAutoRefresh= true)
    protected IssueEntity parent;

    public void setParent(IssueEntity parent) {

        this.parent = parent;
    }

    public IssueEntity getParent() {
        return parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTracker() {
        return trackerName;
    }

    public void setTracker(String tracker) {
        this.trackerName = tracker;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public static Collection<Long> concertItems(List<Child> childList, IssueEntity parent) {

        if (childList == null) {
            return null;
        }

        Collection<Long> idsList = new ArrayList<>();
        ChildEntityDAO childEntityDAO = DatabaseManager.getDatabaseHelper().getChildEntityDAO();
        childEntityDAO.deleteExtraEntitiesFromBd(childList);
        try {

            //childEntityDAO.delete(childEntityDAO.getAll());

            for (Child child : childList) {

                ChildEntity childEntity = new ChildEntity();
                childEntity.setParent(parent);
                childEntity.setId(child.getId());
                if (child.getTracker() != null) {
                    childEntity.setTracker(child.getTracker().getName());
                }
                childEntity.setSubject(child.getSubject());

                childEntityDAO.createOrUpdate(childEntity);

                idsList.add(childEntity.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idsList;
    }
}
