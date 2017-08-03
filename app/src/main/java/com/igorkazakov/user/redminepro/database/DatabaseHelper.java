package com.igorkazakov.user.redminepro.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.igorkazakov.user.redminepro.database.dao.AttachmentEntityDAO;
import com.igorkazakov.user.redminepro.database.dao.CalendarDayDAO;
import com.igorkazakov.user.redminepro.database.dao.ChildEntityDAO;
import com.igorkazakov.user.redminepro.database.dao.DetailEntityDAO;
import com.igorkazakov.user.redminepro.database.dao.IssueEntityDAO;
import com.igorkazakov.user.redminepro.database.dao.JournalEntityDAO;
import com.igorkazakov.user.redminepro.database.dao.TimeEntryDAO;
import com.igorkazakov.user.redminepro.database.entity.AttachmentEntity;
import com.igorkazakov.user.redminepro.database.entity.CalendarDayEntity;
import com.igorkazakov.user.redminepro.database.entity.ChildEntity;
import com.igorkazakov.user.redminepro.database.entity.DetailEntity;
import com.igorkazakov.user.redminepro.database.entity.IssueEntity;
import com.igorkazakov.user.redminepro.database.entity.JournalEntity;
import com.igorkazakov.user.redminepro.database.entity.TimeEntryEntity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by user on 11.07.17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "local.db";
    private static final int DATABASE_VERSION = 7;

    private TimeEntryDAO mTimeEntryDAO;
    private CalendarDayDAO mCalendarDayDAO;
    private IssueEntityDAO mIssueEntityDAO;
    private AttachmentEntityDAO mAttachmentEntityDAO;
    private ChildEntityDAO mChildEntityDAO;
    private DetailEntityDAO mDetailEntityDAO;
    private JournalEntityDAO mJournalEntityDAO;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TimeEntryEntity.class);
            TableUtils.createTable(connectionSource, CalendarDayEntity.class);
            TableUtils.createTable(connectionSource, IssueEntity.class);
            TableUtils.createTable(connectionSource, AttachmentEntity.class);
            TableUtils.createTable(connectionSource, ChildEntity.class);
            TableUtils.createTable(connectionSource, DetailEntity.class);
            TableUtils.createTable(connectionSource, JournalEntity.class);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, TimeEntryEntity.class, true);
            TableUtils.dropTable(connectionSource, CalendarDayEntity.class, true);
            TableUtils.dropTable(connectionSource, IssueEntity.class, true);
            TableUtils.dropTable(connectionSource, AttachmentEntity.class, true);
            TableUtils.dropTable(connectionSource, ChildEntity.class, true);
            TableUtils.dropTable(connectionSource, DetailEntity.class, true);
            TableUtils.dropTable(connectionSource, JournalEntity.class, true);
            onCreate(database, connectionSource);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public AttachmentEntityDAO getAttachmentEntityDAO() {

        if (mAttachmentEntityDAO == null) {
            try {
                mAttachmentEntityDAO = new AttachmentEntityDAO(getConnectionSource(), AttachmentEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return mAttachmentEntityDAO;
    }

    public ChildEntityDAO getChildEntityDAO() {

        if (mChildEntityDAO == null) {
            try {
                mChildEntityDAO = new ChildEntityDAO(getConnectionSource(), ChildEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return mChildEntityDAO;
    }

    public DetailEntityDAO getDetailEntityDAO() {

        if (mDetailEntityDAO == null) {
            try {
                mDetailEntityDAO = new DetailEntityDAO(getConnectionSource(), DetailEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return mDetailEntityDAO;
    }

    public JournalEntityDAO getJournalEntityDAO() {

        if (mJournalEntityDAO == null) {
            try {
                mJournalEntityDAO = new JournalEntityDAO(getConnectionSource(), JournalEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return mJournalEntityDAO;
    }

    public IssueEntityDAO getIssueEntityDAO() {

        if (mIssueEntityDAO == null) {
            try {
                mIssueEntityDAO = new IssueEntityDAO(getConnectionSource(), IssueEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return mIssueEntityDAO;
    }

    public TimeEntryDAO getTimeEntryDAO() {

        if (mTimeEntryDAO == null) {
            try {
                mTimeEntryDAO = new TimeEntryDAO(getConnectionSource(), TimeEntryEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return mTimeEntryDAO;
    }

    public CalendarDayDAO getCalendarDayDAO() {

        if (mCalendarDayDAO == null) {
            try {
                mCalendarDayDAO = new CalendarDayDAO(getConnectionSource(), CalendarDayEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return mCalendarDayDAO;
    }

    @Override
    public void close() {
        super.close();
    }
}
