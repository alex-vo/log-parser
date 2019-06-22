package com.ef.util;

import com.ef.entity.LogEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AccessLogParsingUtils.class)
public class AccessLogParsingUtilsTest {

    private static final String ACCESS_LOG_CONTENT = "2017-01-01 16:34:12.082|192.168.181.13|\"GET / HTTP/1.1\"|200|\"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; EIE10;ENUSWOL)\"\n" +
            "2017-01-01 16:34:12.337|192.168.109.184|\"GET / HTTP/1.1\"|200|\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.91 Safari/537.36\"\n" +
            "2017-01-01 16:34:12.441|192.168.125.32|\"GET / HTTP/1.1\"|200|\"Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Mobile Safari/537.36\"\n" +
            "||||\n" + //unparseable string
            "2017-01-01 16:34:12.447|192.168.198.201|\"GET / HTTP/1.1\"|200|\"Mozilla/5.0 (Linux; Android 7.0; Moto G (4) Build/NPJS25.93-14-8) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.116 Mobile Safari/537.36\"";

    @Test
    public void testParsing() throws Exception {
        BufferedReader reader = new BufferedReader(new StringReader(ACCESS_LOG_CONTENT));
        whenNew(FileReader.class).withAnyArguments().thenReturn(mock(FileReader.class));
        whenNew(BufferedReader.class).withAnyArguments().thenReturn(reader);

        List<LogEntry> result = AccessLogParsingUtils.parseFile("blabla");

        assertThat(result, contains(
                allOf(hasProperty("ip", is("192.168.181.13"))),
                allOf(hasProperty("ip", is("192.168.109.184"))),
                allOf(hasProperty("ip", is("192.168.125.32"))),
                allOf(hasProperty("ip", is("192.168.198.201")))
        ));
    }

}
