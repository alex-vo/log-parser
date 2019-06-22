package com.ef.service;

import com.ef.args.Arguments;
import com.ef.args.Duration;
import com.ef.entity.LogEntry;
import com.ef.repository.LogEntryRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.List;

@Singleton
@Slf4j
public class LogEntryService {

    @Inject
    private LogEntryRepository logEntryRepository;

    public List<String> listIpsExceedingRequestThreshold(Arguments arguments) {
        ZonedDateTime endDate = getEndDateFromArguments(arguments);

        return logEntryRepository.listIpsExceedingRequestThreshold(arguments.getStartDate(),
                endDate, arguments.getThreshold());
    }

    public void saveLogEntries(List<LogEntry> logEntries) {
        log.info("Saving {} log entries...", logEntries.size());
        logEntryRepository.persistLogEntries(logEntries);
        log.info("Finished saving log entries");
    }

    private ZonedDateTime getEndDateFromArguments(Arguments arguments) {
        ZonedDateTime endDate;
        if (arguments.getDuration() == Duration.DAILY) {
            endDate = arguments.getStartDate().plusDays(1);
        } else if (arguments.getDuration() == Duration.HOURLY) {
            endDate = arguments.getStartDate().plusHours(1);
        } else {
            throw new IllegalArgumentException("Invalid duration " + arguments.getDuration());
        }
        return endDate;
    }

}
