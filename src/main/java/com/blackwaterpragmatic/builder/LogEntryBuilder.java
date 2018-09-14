package com.blackwaterpragmatic.builder;

import com.blackwaterpragmatic.bean.LogEntry;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class LogEntryBuilder {

	private static final int DATE_INDEX = 0;
	private static final int IP_INDEX = 1;
	private static final int REQUEST_INDEX = 2;
	private static final int STATUS_INDEX = 3;
	private static final int USER_AGENT_INDEX = 4;

	private static final String DELIMITER_REGEX = "\\|";

	public LogEntry buildFromTextLogEntry(final String textLogEntry) throws ParseException {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		final String[] logEntryComponents = textLogEntry.split(DELIMITER_REGEX);
		return new LogEntry() {
			{
				setRequestDate(simpleDateFormat.parse(logEntryComponents[DATE_INDEX]));
				setIpAddress(logEntryComponents[IP_INDEX]);
				setRequest(logEntryComponents[REQUEST_INDEX].replaceAll("\"", ""));
				setStatus(Integer.parseInt(logEntryComponents[STATUS_INDEX]));
				setUserAgent(logEntryComponents[USER_AGENT_INDEX].replaceAll("\"", ""));
			}
		};
	}

}
