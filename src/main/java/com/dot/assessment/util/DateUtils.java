package com.dot.assessment.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateUtils {
    public static LocalDateTime getDateTime(String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime formatDateTime = null;
        try {
            log.info(" parsing date-time [{}]", dateTime);
            formatDateTime = LocalDateTime.parse(dateTime, formatter);
        }catch (Exception e){
            log.error(" Generic Error occurred ::: [{}]", e.getMessage());
        }
        return formatDateTime;
    }
}
