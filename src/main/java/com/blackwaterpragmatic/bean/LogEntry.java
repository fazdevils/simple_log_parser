package com.blackwaterpragmatic.bean;

import java.util.Date;

public class LogEntry {
	private Long id;
	private Date requestDate;
	private String ipAddress;
	private String request;
	private Integer status;
	private String userAgent;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(final Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(final String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(final String request) {
		this.request = request;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(final String userAgent) {
		this.userAgent = userAgent;
	}

}
