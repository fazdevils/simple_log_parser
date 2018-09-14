package com.blackwaterpragmatic.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.blackwaterpragmatic.bean.LogEntry;
import com.blackwaterpragmatic.bean.SearchResult;
import com.blackwaterpragmatic.mybatis.mapper.LogEntryMapper;
import com.blackwaterpragmatic.service.LogEntryService;
import com.blackwaterpragmatic.test.MockHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class LogEntryServiceTest {

	@Mock
	private LogEntryMapper logEntryMapper;

	@InjectMocks
	private LogEntryService logEntryService;

	@Test
	public void should_add_log_entries() {
		final List<LogEntry> logEntries = new ArrayList<LogEntry>() {
			{
				add(new LogEntry());
				add(new LogEntry());
			}
		};

		logEntryService.addLogEntries(logEntries);

		verify(logEntryMapper, times(2)).insert(any(LogEntry.class));
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));
	}


	@Test
	public void should_search() {
		final Date startDate = new Date();
		final Date endDate = new Date();
		final Integer threshold = 1;

		final List<SearchResult> matches = new ArrayList<SearchResult>() {
			{
				add(new SearchResult());
				add(new SearchResult());
			}
		};

		when(logEntryMapper.search(startDate, endDate, threshold)).thenReturn(matches);

		final List<SearchResult> actualSearchResults = logEntryService.search(startDate, endDate, threshold);

		verify(logEntryMapper).search(startDate, endDate, threshold);
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));

		assertEquals(matches, actualSearchResults);
	}

	@Test
	public void should_add_searhc_results() {
		final List<SearchResult> searchResults = new ArrayList<SearchResult>() {
			{
				add(new SearchResult());
				add(new SearchResult());
			}
		};

		logEntryService.addSearchResults(searchResults);

		verify(logEntryMapper, times(2)).insertSearchResult(any(SearchResult.class));
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));
	}


	@Test
	public void should_list_search_results() {
		final List<SearchResult> searchResults = new ArrayList<SearchResult>() {
			{
				add(new SearchResult());
				add(new SearchResult());
			}
		};

		when(logEntryMapper.listSearchResults()).thenReturn(searchResults);

		final List<SearchResult> actualSearchResults = logEntryService.listSearchResults();

		verify(logEntryMapper).listSearchResults();
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));

		assertEquals(searchResults, actualSearchResults);
	}

}
