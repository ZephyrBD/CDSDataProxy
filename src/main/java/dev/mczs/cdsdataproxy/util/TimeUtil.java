package dev.mczs.cdsdataproxy.util;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeUtil {
    /**
     * ISO时间转UNIX时间戳
     * @param isoTime ISO格式字符串（如 "2025-10-26T09:03:13.000+08:00"）
     * @return 如 "1543055342.998196000"
     */
    public static String isoToUnix(String isoTime) {
        if(isoTime == null || isoTime.isEmpty()){
            return "0.000000000";
        }
        try {
            OffsetDateTime odt = OffsetDateTime.parse(isoTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            double timestamp = odt.toInstant().toEpochMilli() / 1000.00;
            return String.format("%.9f", timestamp);
        } catch (DateTimeParseException e) {
            return "0.000000000";
        }
    }
}
