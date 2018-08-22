package com.igorkazakov.user.redminepro.database.room.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.igorkazakov.user.redminepro.database.room.dao.AttachmentEntityDAO;
import com.igorkazakov.user.redminepro.database.room.dao.ChildEntityDAO;
import com.igorkazakov.user.redminepro.database.room.dao.DetailEntityDAO;
import com.igorkazakov.user.redminepro.database.room.dao.FixedVersionEntityDAO;
import com.igorkazakov.user.redminepro.database.room.dao.IssueDetailEntityDAO;
import com.igorkazakov.user.redminepro.database.room.dao.IssueEntityDAO;
import com.igorkazakov.user.redminepro.database.room.dao.JournalsEntityDAO;
import com.igorkazakov.user.redminepro.database.room.dao.OggyCalendarDayDAO;
import com.igorkazakov.user.redminepro.database.room.dao.PriorityEntityDAO;
import com.igorkazakov.user.redminepro.database.room.dao.ProjectEntityDAO;
import com.igorkazakov.user.redminepro.database.room.dao.ShortUserEntityDAO;
import com.igorkazakov.user.redminepro.database.room.dao.StatusEntityDAO;
import com.igorkazakov.user.redminepro.database.room.dao.TimeEntryEntityDAO;
import com.igorkazakov.user.redminepro.database.room.dao.TrackerEntityDAO;
import com.igorkazakov.user.redminepro.database.room.entity.AttachmentEntity;
import com.igorkazakov.user.redminepro.database.room.entity.ChildEntity;
import com.igorkazakov.user.redminepro.database.room.entity.DetailEntity;
import com.igorkazakov.user.redminepro.database.room.entity.FixedVersionEntity;
import com.igorkazakov.user.redminepro.database.room.entity.IssueDetailEntity;
import com.igorkazakov.user.redminepro.database.room.entity.IssueEntity;
import com.igorkazakov.user.redminepro.database.room.entity.JournalsEntity;
import com.igorkazakov.user.redminepro.database.room.entity.OggyCalendarDayEntity;
import com.igorkazakov.user.redminepro.database.room.entity.PriorityEntity;
import com.igorkazakov.user.redminepro.database.room.entity.ProjectEntity;
import com.igorkazakov.user.redminepro.database.room.entity.ShortUserEntity;
import com.igorkazakov.user.redminepro.database.room.entity.StatusEntity;
import com.igorkazakov.user.redminepro.database.room.entity.TimeEntryEntity;
import com.igorkazakov.user.redminepro.database.room.entity.TrackerEntity;

@Database(entities = {
        OggyCalendarDayEntity.class,
        TimeEntryEntity.class,
        PriorityEntity.class,
        ProjectEntity.class,
        StatusEntity.class,
        TrackerEntity.class,
        ShortUserEntity.class,
        FixedVersionEntity.class,
        AttachmentEntity.class,
        ChildEntity.class,
        DetailEntity.class,
        IssueDetailEntity.class,
        IssueEntity.class,
        JournalsEntity.class}, version = 6)

public abstract class RoomDbHelper extends RoomDatabase {

    public abstract OggyCalendarDayDAO oggyCalendarDayEntityDAO();
    public abstract PriorityEntityDAO priorityEntityDAO();
    public abstract ProjectEntityDAO projectEntityDAO();
    public abstract StatusEntityDAO statusEntityDAO();
    public abstract TrackerEntityDAO trackerEntityDAO();
    public abstract TimeEntryEntityDAO timeEntryEntityDAO();
    public abstract ShortUserEntityDAO shortUserEntityDAO();
    public abstract FixedVersionEntityDAO fixedVersionEntityDAO();
    public abstract AttachmentEntityDAO attachmentEntityDAO();
    public abstract ChildEntityDAO childEntityDAO();
    public abstract DetailEntityDAO detailEntityDAO();
    public abstract IssueDetailEntityDAO issueDetailEntityDAO();
    public abstract IssueEntityDAO issueEntityDAO();
    public abstract JournalsEntityDAO journalsEntityDAO();
}
