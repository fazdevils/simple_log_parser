package com.blackwaterpragmatic.service;

import com.blackwaterpragmatic.bean.LogEntry;
import com.blackwaterpragmatic.builder.LogEntryBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogParsingService {

	private final LogEntryBuilder logEntryBuilder;

	@Autowired
	public LogParsingService(
			final LogEntryBuilder logEntryBuilder) {
		this.logEntryBuilder = logEntryBuilder;
	}

	public List<LogEntry> parseLog(final String logFilename) throws FileNotFoundException, IOException, ParseException {
		final List<LogEntry> logEntries = new ArrayList<>();
		final ClassPathResource logFileResource = new ClassPathResource(logFilename);
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(logFileResource.getInputStream()))) {
			String textLogEntry;
			while ((textLogEntry = fileReader.readLine()) != null) {
				final LogEntry logEntry = logEntryBuilder.buildFromTextLogEntry(textLogEntry);
				logEntries.add(logEntry);
			}
		}
		return logEntries;
	}

}
