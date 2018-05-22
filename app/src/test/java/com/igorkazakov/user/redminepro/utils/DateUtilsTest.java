package com.igorkazakov.user.redminepro.utils;

import com.igorkazakov.user.redminepro.models.TimeInterval;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DateUtilsTest {

    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleDateFormatterWithTime;

    @Before
    public void setUp() throws Exception {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormatterWithTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        simpleDateFormatterWithTime.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Test
    public void dateFromString() {

        String strDate = "1994-06-07";
        long actualTimestamp = DateUtils.dateFromString(strDate, simpleDateFormat).getTime();
        long expectedTimestamp = 770947200000L;
        assertEquals(expectedTimestamp, actualTimestamp);

        strDate = "1994-06-07T10:11:12Z";
        actualTimestamp = DateUtils.dateFromString(strDate, simpleDateFormatterWithTime).getTime();
        expectedTimestamp = 770983872000L;
        assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    public void stringFromDate() {

        Date date = new Date(770947200000L);
        String expectedString = "1994-06-07";
        String actualString = DateUtils.stringFromDate(date, simpleDateFormat);
        assertEquals(expectedString, actualString);

        date = new Date(770983872000L);
        expectedString = "1994-06-07T10:11:12Z";
        actualString = DateUtils.stringFromDate(date, simpleDateFormatterWithTime);
        assertEquals(expectedString, actualString);
    }

    @Test
    public void getYesterday() {

        long expectedDate = 1527022800000L;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(1527109200000L);

        long actualDate = DateUtils.getYesterday(calendar).getTime();
        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void getCurrentYear() { }

    @Test
    public void getCurrentMonth() { }

    @Test
    public void getMonday() {

        Date date = new Date(1527109200000L);
        long expectedTimestamp = 1526850000000L;
        long actualTimestamp = DateUtils.getMonday(date).getTime();
        assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    public void getPreviousWeekInterval() {

        Date date = new Date(1527022800000L);
        Date weekStartDate = new Date(1526245200000L);
        Date weekEndDate = new Date(1526763600000L);
        TimeInterval expectedTimeInterval =
                new TimeInterval(weekStartDate, weekEndDate);

        TimeInterval actualTimeInterval = DateUtils.getPreviousWeekInterval(date);
        assertTrue(expectedTimeInterval.equals(actualTimeInterval));
    }

    @Test
    public void getCurrentWeekInterval() {
        Date nowDate = new Date(1527022800000L);
        Date weekStartDate = new Date(1526850000000L);
        TimeInterval expectedTimeInterval =
                new TimeInterval(weekStartDate, nowDate);

        TimeInterval actualTimeInterval = DateUtils.getCurrentWeekInterval(nowDate);
        assertTrue(expectedTimeInterval.equals(actualTimeInterval));
    }

    @Test
    public void getCurrentMonthInterval() {
        Date nowDate = new Date(1527022800000L);
        Date monthStartDate = new Date(1525122000000L);
        TimeInterval expectedTimeInterval =
                new TimeInterval(monthStartDate, nowDate);

        TimeInterval actualTimeInterval = DateUtils.getCurrentMonthInterval(nowDate);
        assertTrue(expectedTimeInterval.equals(actualTimeInterval));
    }

    @Test
    public void getCurrentWholeWeekInterval() {
        Date nowDate = new Date(1527022800000L);
        Date weekStartDate = new Date(1526850000000L);
        Date weekEndDate = new Date(1527368400000L);
        TimeInterval expectedTimeInterval =
                new TimeInterval(weekStartDate, weekEndDate);

        TimeInterval actualTimeInterval = DateUtils.getCurrentWholeWeekInterval(nowDate);
        assertTrue(expectedTimeInterval.equals(actualTimeInterval));
    }

    @Test
    public void getMonthInterval() {
        Date nowDate = new Date(1527022800000L);
        Date monthStartDate = new Date(1525122000000L);
        Date monthEndDate = new Date(1527714000000L);
        TimeInterval expectedTimeInterval =
                new TimeInterval(monthStartDate, monthEndDate);

        TimeInterval actualTimeInterval = DateUtils.getMonthInterval(nowDate, 4);
        assertTrue(expectedTimeInterval.equals(actualTimeInterval));
    }

    @Test
    public void getIntervalFromStartYear() {

        Date nowDate = new Date(1527022800000L);
        Date yearStartDate = new Date(1514754000000L);

        TimeInterval expectedTimeInterval =
                new TimeInterval(yearStartDate, nowDate);

        TimeInterval actualTimeInterval = DateUtils.getIntervalFromStartYear(nowDate);
        assertTrue(expectedTimeInterval.equals(actualTimeInterval));
    }

    @Test
    public void timeDifference() {

        Date nowDate = new Date(1526850000000L);

        Date oldDate = new Date(1432155600000L);
        String actualString = DateUtils.timeDifference(nowDate, oldDate);
        assertEquals("3 years", actualString);

        oldDate = new Date(1524258000000L);
        actualString = DateUtils.timeDifference(nowDate, oldDate);
        assertEquals("1 month", actualString);

        oldDate = new Date(1526677200000L);
        actualString = DateUtils.timeDifference(nowDate, oldDate);
        assertEquals("2 days", actualString);

        oldDate = new Date(1526850300000L);
        actualString = DateUtils.timeDifference(nowDate, oldDate);
        assertEquals("5 minutes", actualString);

        oldDate = new Date(1526850030000L);
        actualString = DateUtils.timeDifference(nowDate, oldDate);
        assertEquals("30 seconds", actualString);
    }
}