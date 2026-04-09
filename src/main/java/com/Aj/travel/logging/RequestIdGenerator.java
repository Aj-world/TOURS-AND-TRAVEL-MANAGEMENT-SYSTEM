package com.aj.travel.logging;

import java.util.UUID;

import org.slf4j.MDC;

public final class RequestIdGenerator {

	public static final String MDC_KEY = "requestId";

	private RequestIdGenerator() {
	}

	public static String ensureRequestId() {
		String requestId = MDC.get(MDC_KEY);
		if (requestId == null || requestId.isBlank()) {
			requestId = UUID.randomUUID().toString();
			MDC.put(MDC_KEY, requestId);
		}
		return requestId;
	}

	public static String getRequestId() {
		return MDC.get(MDC_KEY);
	}

	public static void clear() {
		MDC.remove(MDC_KEY);
	}
}
