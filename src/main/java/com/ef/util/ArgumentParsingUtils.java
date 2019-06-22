package com.ef.util;

import com.ef.args.Arguments;
import com.ef.args.Duration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.ZoneOffset.UTC;

@Slf4j
public class ArgumentParsingUtils {

    private static final String THRESHOLD_ARGUMENT_NAME = "--threshold";
    private static final String DURATION_ARGUMENT_NAME = "--duration";
    private static final String ACCESS_LOG_ARGUMENT_NAME = "--accesslog";
    private static final String START_DATE_ARGUMENT_NAME = "--startDate";
    private static final DateTimeFormatter START_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");

    public static Arguments parseArguments(String[] args) {
        Duration duration = null;
        String accessLogFileLocation = null;
        Integer threshold = null;
        ZonedDateTime startDate = null;
        for (String argument : args) {
            if (StringUtils.startsWith(argument.trim(), THRESHOLD_ARGUMENT_NAME + "=")) {
                threshold = Integer.parseInt(argument.replaceFirst(THRESHOLD_ARGUMENT_NAME + "=", ""));
            } else if (StringUtils.startsWith(argument.trim(), DURATION_ARGUMENT_NAME + "=")) {
                try {
                    duration = Duration.valueOf(argument.replaceFirst(DURATION_ARGUMENT_NAME + "=", "").toUpperCase());
                } catch (IllegalArgumentException e) {
                    log.error(String.format("Unknown duration value '%s'", argument), e);
                }
            } else if (StringUtils.startsWith(argument.trim(), ACCESS_LOG_ARGUMENT_NAME + "=")) {
                accessLogFileLocation = argument.replaceFirst(ACCESS_LOG_ARGUMENT_NAME + "=", "");
            } else if (StringUtils.startsWith(argument.trim(), START_DATE_ARGUMENT_NAME + "=")) {
                startDate = LocalDateTime.parse(argument.replaceFirst(START_DATE_ARGUMENT_NAME + "=", ""), START_DATE_TIME_FORMATTER)
                        .atZone(UTC);
            } else {
                log.error(String.format("Unknown argument '%s'", argument));
            }
        }

        if (duration == null) {
            throw new IllegalArgumentException("Duration not supplied");
        }
        if (accessLogFileLocation == null) {
            throw new IllegalArgumentException("Access log file location not supplied");
        }
        if (threshold == null) {
            throw new IllegalArgumentException("Threshold not supplied");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start date not supplied");
        }

        return new Arguments(duration, startDate, threshold, accessLogFileLocation);
    }

}
