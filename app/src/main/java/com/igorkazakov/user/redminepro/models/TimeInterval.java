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

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof TimeInterval) {
            TimeInterval timeInterval = (TimeInterval) obj;

            return getStart().getTime() == timeInterval.getStart().getTime() &&
                    getEnd().getTime() == timeInterval.getEnd().getTime();
        }

        return false;
    }
}
