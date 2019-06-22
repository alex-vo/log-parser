package com.ef.util;

import com.ef.entity.LogEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.ZoneOffset.UTC;

@Slf4j
public class AccessLogParsingUtils {

    public static final char DELIMITER = '|';
    public static final DateTimeFormatter LOG_ENTRY_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static List<LogEntry> parseFile(String filePath) throws IOException {
        log.info("Parsing in progress...");
        List<LogEntry> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while (line != null) {
                LogEntry logEntry = parseString(line);
                if (logEntry != null) {
                    result.add(logEntry);
                }
                line = reader.readLine();
            }
        }

        log.info("Finished parsing. Got {} entries", result.size());
        return result;
    }

    private static LogEntry parseString(String str) {
        try {
            String[] array = StringUtils.split(str, DELIMITER);
            ZonedDateTime entryDate = LocalDateTime.parse(array[0], LOG_ENTRY_DATE_TIME_FORMATTER)
                    .atZone(UTC);
            String ip = array[1];
            String method = array[2];
            Integer status = Integer.parseInt(array[3]);
            String message = array[4];
            return new LogEntry(entryDate, ip, method, status, message);
        } catch (RuntimeException e) {
            log.error(String.format("Failed to parse '%s'", str), e);
        }

        return null;
    }


}
