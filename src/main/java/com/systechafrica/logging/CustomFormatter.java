package com.systechafrica.logging;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String method = record.getSourceMethodName();
        String level = record.getLevel().getName();
        String message = record.getMessage();
        Instant instant = record.getInstant();
        LocalDateTime now = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return pattern.format(now) + " | " + level + " | " + method + " | " + message +"\n";
    }

}
