package com.aj.travel.controller;

import static com.aj.travel.constants.SecurityConstants.HAS_ROLE_USER;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aj.travel.dto.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/about")
@PreAuthorize(HAS_ROLE_USER)
@Slf4j
public class AboutController {

	@GetMapping
	public ResponseEntity<ApiResponse<Map<String, String>>> showAboutPage() {
		log.debug("API request: show about data");
		return ResponseEntity.ok(ApiResponse.success(
				"About data fetched",
				Map.of("message", "Tours and Travel Management API")));
	}
}
