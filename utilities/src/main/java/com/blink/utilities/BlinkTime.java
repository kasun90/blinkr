package com.blink.utilities;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class BlinkTime {

    private static DateTimeZone timeZone = DateTimeZone.UTC;


    public static long getCurrentTimeMillis() {
        return DateTime.now(timeZone).getMillis();
    }

    public static DateTime fromTimestamp(long epochVal) {
        return new DateTime(epochVal, timeZone);
    }

    public static String format(long epochVal, String format) {
        return new DateTime(epochVal, timeZone).toString(format);
    }


}
