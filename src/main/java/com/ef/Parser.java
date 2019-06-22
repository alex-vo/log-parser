package com.ef;

import com.ef.args.Arguments;
import com.ef.entity.LockedIp;
import com.ef.entity.LogEntry;
import com.ef.service.LockedIpService;
import com.ef.service.LogEntryService;
import com.ef.util.AccessLogParsingUtils;
import com.ef.util.ArgumentParsingUtils;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Parser {

    @Inject
    private LogEntryService logEntryService;
    @Inject
    private LockedIpService lockedIpService;

    public static void main(String[] args) throws IOException {
        Arguments arguments = ArgumentParsingUtils.parseArguments(args);

        Parser parser = new Parser();

        AccessLogParserModule module = new AccessLogParserModule();

        Injector injector = Guice.createInjector(module);
        injector.injectMembers(parser);

        List<LogEntry> logEntriesFromFile = AccessLogParsingUtils.parseFile(arguments.getAccessLogFileLocation());
        parser.logEntryService.saveLogEntries(logEntriesFromFile);

        List<String> ipsExceedingRequestThreshold = parser.logEntryService.listIpsExceedingRequestThreshold(arguments);

        parser.lockedIpService.saveLockedIps(ipsExceedingRequestThreshold.stream()
                .map(ip -> new LockedIp(ip, String.format("Made more than %d requests %s starting from %s", arguments.getThreshold(),
                        arguments.getDuration().name().toLowerCase(), arguments.getStartDate().format(AccessLogParsingUtils.LOG_ENTRY_DATE_TIME_FORMATTER))))
                .collect(Collectors.toList()));

        printResults(ipsExceedingRequestThreshold, arguments);
    }

    private static void printResults(List<String> ips, Arguments arguments) {
        log.info("Printing results...");
        if (ips != null && ips.size() > 0) {
            log.info(String.format("Following IPs made more than %d requests %s starting from %s", arguments.getThreshold(),
                    arguments.getDuration().name().toLowerCase(), arguments.getStartDate().format(AccessLogParsingUtils.LOG_ENTRY_DATE_TIME_FORMATTER)));
            ips.forEach(log::info);
        }
        log.info("Done.");
    }

}
