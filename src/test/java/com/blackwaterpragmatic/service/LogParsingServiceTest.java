package com.blackwaterpragmatic.service;

import static org.junit.Assert.assertEquals;

import com.blackwaterpragmatic.bean.LogEntry;
import com.blackwaterpragmatic.builder.LogEntryBuilder;
import com.blackwaterpragmatic.service.LogParsingService;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class LogParsingServiceTest {

	private final LogParsingService logParsingService = new LogParsingService(new LogEntryBuilder());

	@Test
	public void should_parse_log_file() throws FileNotFoundException, IOException, ParseException {
		final List<LogEntry> logEntries = logParsingService.parseLog("test-access.log");

		assertEquals(2, logEntries.size());
	}

}
