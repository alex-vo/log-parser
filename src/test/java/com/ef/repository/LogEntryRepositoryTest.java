package com.ef.repository;

import com.ef.IntegrationTest;
import com.ef.entity.LogEntry;
import com.google.inject.Inject;
import org.junit.Before;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static java.time.ZoneOffset.UTC;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LogEntryRepositoryTest extends IntegrationTest {

    @Inject
    private LogEntryRepository logEntryRepository;

    @Before
    public void setUp() {
        setUpInjector(this);
    }

    @Test
    public void testListLogEntries() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2019, 1, 1, 0, 0, 0, 0, UTC);

        LogEntry e1 = new LogEntry(zonedDateTime, "102.155.12.1", "GET", 200, "ok");
        LogEntry e2 = new LogEntry(zonedDateTime.plusMinutes(59), "192.168.234.82", "GET", 200, "ok");
        LogEntry e3 = new LogEntry(zonedDateTime.plusMinutes(61), "192.168.169.194", "GET", 200, "ok");

        logEntryRepository.persistLogEntries(Arrays.asList(e1, e2, e3));

        List<String> list = logEntryRepository.listIpsExceedingRequestThreshold(zonedDateTime, zonedDateTime.plusHours(1), 0);

        assertThat(list, is(Arrays.asList("102.155.12.1", "192.168.234.82")));
    }

}
