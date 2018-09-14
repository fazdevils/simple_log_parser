package com.blackwaterpragmatic.mybatis.mapper;

import com.blackwaterpragmatic.bean.LogEntry;
import com.blackwaterpragmatic.bean.SearchResult;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LogEntryMapper {

	List<LogEntry> searchForIp(
			@Param("ipAddress") String ipAddress);

	List<SearchResult> search(
			@Param("startDate") Date startDate,
			@Param("endDate") Date endDate,
			@Param("threshold") Integer threshold);

	void insert(LogEntry logEntry);

	void insertSearchResult(SearchResult searchResult);

	List<SearchResult> listSearchResults();

}
