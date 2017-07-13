package com.igorkazakov.user.redminepro.api.response;

import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.TimeEntry;

import java.util.List;

/**
 * Created by user on 13.07.17.
 */

public class TimeEntryResponse {

    public List<TimeEntry> getTimeEntries() {
        return timeEntries;
    }

    public void setTimeEntries(List<TimeEntry> timeEntries) {
        this.timeEntries = timeEntries;
    }

    @SerializedName("time_entries")
    private List<TimeEntry> timeEntries;
}
