package com.blackwaterpragmatic.service;

import com.blackwaterpragmatic.bean.LogEntry;
import com.blackwaterpragmatic.bean.SearchResult;
import com.blackwaterpragmatic.mybatis.mapper.LogEntryMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogEntryService {

	private final LogEntryMapper logEntryMapper;

	@Autowired
	public LogEntryService(
			final LogEntryMapper logEntryMapper) {
		this.logEntryMapper = logEntryMapper;
	}

	public List<SearchResult> search(final Date startDate, final Date endDate, final Integer threshold) {
		return logEntryMapper.search(startDate, endDate, threshold);
	}

	public void addLogEntries(final List<LogEntry> logEntries) {
		for (final LogEntry logEntry : logEntries) {
			logEntryMapper.insert(logEntry);
		}
	}

	public List<SearchResult> listSearchResults() {
		return logEntryMapper.listSearchResults();
	}

	public void addSearchResults(final List<SearchResult> searchResults) {
		for (final SearchResult searchResult : searchResults) {
			logEntryMapper.insertSearchResult(searchResult);
		}
	}
}
