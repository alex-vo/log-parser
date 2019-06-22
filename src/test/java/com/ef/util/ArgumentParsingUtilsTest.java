package com.ef.util;

import com.ef.args.Arguments;
import com.ef.args.Duration;
import org.junit.Test;

import java.time.ZonedDateTime;

import static java.time.ZoneOffset.UTC;
import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ArgumentParsingUtilsTest {

    @Test
    public void testParseArguments_AccessLogFileLocationNotSupplied() {
        try {
            ArgumentParsingUtils.parseArguments(new String[]{"--startDate=2017-01-01.13:00:00", "--duration=hourly",
                    "--threshold=2"});
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Access log file location not supplied"));
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testParseArguments_StartDateNotSupplied() {
        try {
            ArgumentParsingUtils.parseArguments(new String[]{"--accesslog=/home/alex/trash/minified_access.log",
                    "--duration=hourly", "--threshold=2"});
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Start date not supplied"));
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testParseArguments_DurationNotSupplied() {
        try {
            ArgumentParsingUtils.parseArguments(new String[]{"--accesslog=/home/alex/trash/minified_access.log",
                    "--startDate=2017-01-01.13:00:00", "--threshold=2"});
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Duration not supplied"));
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testParseArguments_ThresholdNotSupplied() {
        try {
            ArgumentParsingUtils.parseArguments(new String[]{"--accesslog=/home/alex/trash/minified_access.log",
                    "--startDate=2017-01-01.13:00:00", "--duration=hourly"});
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Threshold not supplied"));
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testParseArguments_AllSuccess() {
        Arguments arguments = ArgumentParsingUtils.parseArguments(new String[]{"--accesslog=/home/alex/trash/minified_access.log",
                "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=2"});
        assertThat(arguments.getAccessLogFileLocation(), is("/home/alex/trash/minified_access.log"));
        assertThat(arguments.getDuration(), is(Duration.HOURLY));
        assertThat(arguments.getStartDate(), is(ZonedDateTime.of(2017, 1, 1, 13, 0, 0, 0, UTC)));
        assertThat(arguments.getThreshold(), is(2));
    }

}
