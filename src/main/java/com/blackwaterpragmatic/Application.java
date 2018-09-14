package com.blackwaterpragmatic;

import com.blackwaterpragmatic.bean.LogEntry;
import com.blackwaterpragmatic.bean.SearchResult;
import com.blackwaterpragmatic.constant.Duration;
import com.blackwaterpragmatic.service.LogEntryService;
import com.blackwaterpragmatic.service.LogParsingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Application implements ApplicationRunner {

	private static final String STARTDATE = "startDate";
	private static final String DURATION = "duration";
	private static final String THRESHOLD = "threshold";

	@Autowired
	private LogEntryService logEntryService;

	@Autowired
	private LogParsingService logParsingService;

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(final ApplicationArguments applicationArguments) throws Exception {
		final Duration duration = initializeDuration(applicationArguments);
		final Date startDate = initializeStartDate(applicationArguments);
		final Date endDate = calculateEndDate(startDate, duration);
		final Integer threshold = initializeThreshold(applicationArguments);

		final List<LogEntry> logEntries = logParsingService.parseLog("access.log");
		logEntryService.addLogEntries(logEntries);

		final List<SearchResult> searchResults = logEntryService.search(startDate, endDate, threshold);

		logEntryService.addSearchResults(searchResults);

		final List<SearchResult> allSearchResults = logEntryService.listSearchResults();
		for (final SearchResult searchResult : allSearchResults) {
			System.out.println(String.format("%s - %d", searchResult.getIpAddress(), searchResult.getCount()));
		}
	}

	private Date calculateEndDate(final Date startDate, final Duration duration) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		switch (duration) {
			case DAILY:
				calendar.add(Calendar.DATE, 1);
				break;
			default:
				calendar.add(Calendar.HOUR, 1);
				break;
		}
		return calendar.getTime();
	}

	private Date initializeStartDate(final ApplicationArguments applicationArguments) throws Exception {
		final boolean containsStartDate = applicationArguments.containsOption(STARTDATE);
		if (containsStartDate) {
			final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
			final List<String> startDateArgs = applicationArguments.getOptionValues(STARTDATE);
			return simpleDateFormat.parse(startDateArgs.get(0));
		}
		throw new Exception(String.format("%s is required", STARTDATE));
	}


	private Duration initializeDuration(final ApplicationArguments applicationArguments) throws Exception {
		final boolean containsDuration = applicationArguments.containsOption(DURATION);
		if (containsDuration) {
			final List<String> durationArgs = applicationArguments.getOptionValues(DURATION);
			return Duration.valueOf(durationArgs.get(0).toUpperCase());
		}
		throw new Exception(String.format("%s is required", DURATION));
	}

	private Integer initializeThreshold(final ApplicationArguments applicationArguments) throws Exception {
		final boolean containsThreshold = applicationArguments.containsOption(THRESHOLD);
		if (containsThreshold) {
			final List<String> thresholdArgs = applicationArguments.getOptionValues(THRESHOLD);
			return Integer.parseInt(thresholdArgs.get(0));
		}
		throw new Exception(String.format("%s is required", THRESHOLD));
	}

}
