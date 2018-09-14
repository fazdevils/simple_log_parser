package com.blackwaterpragmatic.mybatis.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.blackwaterpragmatic.bean.LogEntry;
import com.blackwaterpragmatic.bean.SearchResult;
import com.blackwaterpragmatic.mybatis.mapper.LogEntryMapper;
import com.blackwaterpragmatic.spring.DataConfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ContextConfiguration(classes = {DataConfiguration.class})
@Rollback
@Transactional
@Component
public class LogEntryMapperTest {

	@Autowired
	private LogEntryMapper logEntryMapper;

	@Test
	public void should_insert_and_search() throws ParseException {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		final Date requestDate = simpleDateFormat.parse("2017-01-01 00:00:11.763");
		final String ipAddress = "192.168.234.82";
		final String request = "GET / HTTP/1.1";
		final Integer status = 200;
		final String userAgent = "swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0";

		final LogEntry logEntry = new LogEntry() {
			{
				setRequestDate(requestDate);
				setIpAddress(ipAddress);
				setRequest(request);
				setStatus(status);
				setUserAgent(userAgent);
			}
		};

		logEntryMapper.insert(logEntry);

		final List<LogEntry> logEntries = logEntryMapper.searchForIp("192.168.234.82");

		assertEquals(1, logEntries.size());
		assertNotNull(logEntries.get(0).getId());
		assertEquals(logEntry.getRequestDate(), logEntries.get(0).getRequestDate());
		assertEquals(logEntry.getIpAddress(), logEntries.get(0).getIpAddress());
		assertEquals(logEntry.getRequest(), logEntries.get(0).getRequest());
		assertEquals(logEntry.getStatus(), logEntries.get(0).getStatus());
		assertEquals(logEntry.getUserAgent(), logEntries.get(0).getUserAgent());


		final Date startDate = simpleDateFormat.parse("2017-01-01 00:00:00.000");
		final Date endDate = simpleDateFormat.parse("2017-01-01 01:00:00.000");
		final List<SearchResult> searchResults = logEntryMapper.search(startDate, endDate, 1);

		assertEquals(1, searchResults.size());
		assertEquals("192.168.234.82", searchResults.get(0).getIpAddress());
		assertEquals(1, searchResults.get(0).getCount().intValue());

		final List<SearchResult> searchResults2 = logEntryMapper.search(startDate, endDate, 2);

		assertTrue(searchResults2.isEmpty());
	}

	@Test
	public void should_insert_and_list_search_results() {
		final SearchResult result = new SearchResult() {
			{
				setIpAddress("192.168.234.82");
				setCount(1L);
			}
		};

		logEntryMapper.insertSearchResult(result);

		final List<SearchResult> searchResults = logEntryMapper.listSearchResults();

		assertEquals(1, searchResults.size());
		assertEquals("192.168.234.82", searchResults.get(0).getIpAddress());
		assertEquals(1, searchResults.get(0).getCount().intValue());

	}
}
