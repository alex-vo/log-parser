package com.ef.service;

import com.ef.args.Arguments;
import com.ef.args.Duration;
import com.ef.repository.LogEntryRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogEntryServiceTest {

    @Mock
    private LogEntryRepository logEntryRepository;

    @InjectMocks
    private LogEntryService logEntryService = new LogEntryService();

    @Test(expected = IllegalArgumentException.class)
    public void testListLogEntries_InvalidDuration() {
        logEntryService.listIpsExceedingRequestThreshold(new Arguments(null, null, null, null));
    }

    @Test
    public void testListLogEntries_ListHourlyOrDaily() {
        ZonedDateTime now = ZonedDateTime.now();
        when(logEntryRepository.listIpsExceedingRequestThreshold(now, now.plusHours(1), 2)).thenReturn(Arrays.asList("a", "b"));
        when(logEntryRepository.listIpsExceedingRequestThreshold(now, now.plusDays(1), 2)).thenReturn(Arrays.asList("a", "b", "c"));

        List<String> resultHourly = logEntryService.listIpsExceedingRequestThreshold(new Arguments(Duration.HOURLY, now, 2, "blabla"));
        List<String> resultDaily = logEntryService.listIpsExceedingRequestThreshold(new Arguments(Duration.DAILY, now, 2, "blabla"));

        assertThat(resultHourly, CoreMatchers.is(Arrays.asList("a", "b")));
        assertThat(resultDaily, CoreMatchers.is(Arrays.asList("a", "b", "c")));
    }

}
