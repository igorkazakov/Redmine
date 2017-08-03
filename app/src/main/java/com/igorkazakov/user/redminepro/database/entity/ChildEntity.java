package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Child;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.dao.ChildEntityDAO;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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

    public static ForeignCollection<ChildEntity> concertItems(List<Child> childList, IssueEntity parent) {

        if (childList == null) {
            return null;
        }

        ChildEntityDAO childEntityDAO = DatabaseManager.getDatabaseHelper().getChildEntityDAO();
        childEntityDAO.deleteExtraEntitiesFromBd(childList);


        ForeignCollection<ChildEntity> childEntityCollection = parent.getChildren();
        try {

           // childEntityDAO.delete(childEntityDAO.getAll());

            if (childEntityCollection == null) {
                childEntityCollection = DatabaseManager.getDatabaseHelper().getIssueEntityDAO().getEmptyForeignCollection("children");
            }

            for (Child child : childList) {

                ChildEntity childEntity = new ChildEntity();
                childEntity.setParent(parent);
                childEntity.setId(child.getId());
                if (child.getTracker() != null) {
                    childEntity.setTracker(child.getTracker().getName());
                }
                childEntity.setSubject(child.getSubject());

                if (childEntityCollection != null) {

                    if (childEntityDAO.queryForId(child.getId()) == null) {
                        childEntityCollection.add(childEntity);
                    } else {
                        childEntityCollection.refresh(childEntity);
                    }
                }

                childEntityDAO.createOrUpdate(childEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return childEntityCollection;
    }
}
