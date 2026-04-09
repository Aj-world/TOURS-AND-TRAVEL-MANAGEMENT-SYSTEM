package com.aj.travel.dto;

import java.time.Instant;

public class ApiResponse<T> {

	private final boolean success;
	private final String message;
	private final T data;
	private final Instant timestamp;

	public ApiResponse(boolean success, String message, T data, Instant timestamp) {
		this.success = success;
		this.message = message;
		this.data = data;
		this.timestamp = timestamp;
	}

	public ApiResponse(boolean success, String message, T data) {
		this(success, message, data, Instant.now());
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(true, "Operation successful", data);
	}

	public static <T> ApiResponse<T> success(String message, T data) {
		return new ApiResponse<>(true, message, data);
	}

	public static <T> ApiResponse<T> error(String message) {
		return new ApiResponse<>(false, message, null);
	}
}
