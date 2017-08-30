package com.igorkazakov.user.redminepro.screen.dashboard;

/**
 * Created by Igor on 21.08.2017.
 */

public interface DashboardService {

    void loadStatuses();
    void loadTrackers();
    void loadProjectPriorities();
    void loadProjects();
    void loadCalendarDaysForYear();
    void loadTimeEntriesData();
}
