package com.blackwaterpragmatic.builder;

import static org.junit.Assert.assertEquals;

import com.blackwaterpragmatic.bean.LogEntry;
import com.blackwaterpragmatic.builder.LogEntryBuilder;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class LogEntryBuilderTest {

	private final LogEntryBuilder logEntryBuilder = new LogEntryBuilder();

	@Test
	public void should_build_from_text_log_entry() throws ParseException {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		final String textLogEntry =
				"2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"";

		final LogEntry logEntry = logEntryBuilder.buildFromTextLogEntry(textLogEntry);

		assertEquals("2017-01-01 00:00:11.763", simpleDateFormat.format(logEntry.getRequestDate()));
		assertEquals("192.168.234.82", logEntry.getIpAddress());
		assertEquals("GET / HTTP/1.1", logEntry.getRequest());
		assertEquals(200, logEntry.getStatus().intValue());
		assertEquals("swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0", logEntry.getUserAgent());
	}

}
