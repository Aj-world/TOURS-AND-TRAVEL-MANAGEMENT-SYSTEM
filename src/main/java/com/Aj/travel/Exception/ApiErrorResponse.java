package com.aj.travel.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ApiErrorResponse {

	private final LocalDateTime timestamp;
	private final int status;
	private final String error;
	private final List<String> details;

	public ApiErrorResponse(LocalDateTime timestamp, int status, String error, List<String> details) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.details = details;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public List<String> getDetails() {
		return details;
	}
}
