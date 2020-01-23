package com.artarkatesoft.videomonitoring.videoserver.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {

    private static final long TICKS_AT_EPOCH = 621355968000000000L;
    private static final long TICKS_PER_MILLISECOND = 10000;

    public static long getUTCTicks(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return (calendar.getTimeInMillis() * TICKS_PER_MILLISECOND) + TICKS_AT_EPOCH;

    }

    // TODO: 19.01.2020 UTC time ??? Need something to do (2 hours error)
    //  using Local zone (analogue of DateTime.Now.Ticks)
    //  long tick = (System.currentTimeMillis() + TimeZone.getDefault().getRawOffset()) * 10000 + TICKS_AT_EPOCH;

    public static Date getDate(long UTCTicks) {

        return new Date((UTCTicks - TICKS_AT_EPOCH) / TICKS_PER_MILLISECOND - TimeZone.getDefault().getRawOffset());

    }

}