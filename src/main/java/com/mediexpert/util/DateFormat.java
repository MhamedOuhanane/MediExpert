package com.mediexpert.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormat {

    public static String getDateTimeFormatted(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return dateTime.format(formatter);
    }
}
