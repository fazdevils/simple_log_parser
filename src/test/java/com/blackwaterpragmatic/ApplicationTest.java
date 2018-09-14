package com.blackwaterpragmatic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.blackwaterpragmatic.Application;
import com.blackwaterpragmatic.bean.LogEntry;
import com.blackwaterpragmatic.bean.SearchResult;
import com.blackwaterpragmatic.service.LogEntryService;
import com.blackwaterpragmatic.service.LogParsingService;
import com.blackwaterpragmatic.test.MockHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

	@Mock
	private LogEntryService logEntryService;

	@Mock
	private LogParsingService logParsingService;

	@InjectMocks
	private Application application;


	@Test
	public void should_run_missing_duration_argument() throws Exception {

		try {
			application.run(new DefaultApplicationArguments(new String[0]));
			fail();
		} catch (final Exception e) {
			assertEquals("duration is required", e.getMessage());
		}

		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));
	}

	@Test
	public void should_run_missing_start_date_argument() throws Exception {

		try {
			application.run(new DefaultApplicationArguments(new String[]{
					"--duration=daily"
			}));
			fail();
		} catch (final Exception e) {
			assertEquals("startDate is required", e.getMessage());
		}

		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));
	}

	@Test
	public void should_run_missing_threshold_argument() throws Exception {

		try {
			application.run(new DefaultApplicationArguments(new String[]{
					"--duration=daily",
					"--startDate=2017-01-01.15:00:00"
			}));
			fail();
		} catch (final Exception e) {
			assertEquals("threshold is required", e.getMessage());
		}

		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));
	}

	@Test
	public void should_run_daily() throws Exception {

		final List<LogEntry> logEntries = new ArrayList<LogEntry>() {
			{
				add(new LogEntry());
				add(new LogEntry());
			}
		};

		final List<SearchResult> searchResults = new ArrayList<SearchResult>() {
			{
				add(new SearchResult());
				add(new SearchResult());
			}
		};

		when(logParsingService.parseLog("access.log")).thenReturn(logEntries);
		when(logEntryService.search(any(Date.class), any(Date.class), eq(500))).thenReturn(searchResults);
		when(logEntryService.listSearchResults()).thenReturn(searchResults);

		application.run(new DefaultApplicationArguments(new String[]{
				"--duration=daily",
				"--startDate=2017-01-01.15:00:00",
				"--threshold=500"
		}));

		verify(logParsingService).parseLog("access.log");
		verify(logEntryService).addLogEntries(logEntries);
		verify(logEntryService).search(any(Date.class), any(Date.class), eq(500));
		verify(logEntryService).addSearchResults(searchResults);
		verify(logEntryService).listSearchResults();
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));
	}

	@Test
	public void should_run_hourly() throws Exception {

		final List<LogEntry> logEntries = new ArrayList<>();

		final List<SearchResult> searchResults = new ArrayList<>();

		when(logParsingService.parseLog("access.log")).thenReturn(logEntries);
		when(logEntryService.search(any(Date.class), any(Date.class), eq(500))).thenReturn(searchResults);
		when(logEntryService.listSearchResults()).thenReturn(searchResults);

		application.run(new DefaultApplicationArguments(new String[]{
				"--duration=hourly",
				"--startDate=2017-01-01.15:00:00",
				"--threshold=500"
		}));

		verify(logParsingService).parseLog("access.log");
		verify(logEntryService).addLogEntries(logEntries);
		verify(logEntryService).search(any(Date.class), any(Date.class), eq(500));
		verify(logEntryService).addSearchResults(searchResults);
		verify(logEntryService).listSearchResults();
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));
	}

}
