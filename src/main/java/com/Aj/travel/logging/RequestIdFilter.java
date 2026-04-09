package com.aj.travel.logging;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class RequestIdFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		String requestId = RequestIdGenerator.ensureRequestId();
		try {
			log.debug("HTTP request started | method={} | uri={} | requestId={}",
					request.getMethod(), request.getRequestURI(), requestId);
			filterChain.doFilter(request, response);
			log.debug("HTTP request completed | method={} | uri={} | status={} | requestId={}",
					request.getMethod(), request.getRequestURI(), response.getStatus(), requestId);
		} finally {
			RequestIdGenerator.clear();
		}
	}
}
