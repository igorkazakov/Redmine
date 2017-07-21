package com.igorkazakov.user.redminepro.models;

import java.util.Date;

/**
 * Created by user on 14.07.17.
 */

public class TimeInterval {

    private Date start;

    private Date end;

    public TimeInterval(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }
}
