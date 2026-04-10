package com.aj.travel.controller;

import static com.aj.travel.constants.ApiPaths.HOME;
import static com.aj.travel.constants.ApiPaths.ROOT;
import static com.aj.travel.constants.SecurityConstants.HAS_ROLE_USER;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aj.travel.dto.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping
@Slf4j
public class LoginController {

	@GetMapping(ROOT)
	public ResponseEntity<ApiResponse<Map<String, String>>> apiRoot() {
		log.debug("API request: root endpoint");
		return ResponseEntity.ok(ApiResponse.success(
				"Travel API running",
				Map.of(
						"status", "ok",
						"home", HOME,
						"login", "/auth/login")));
	}

	@PreAuthorize(HAS_ROLE_USER)
	@GetMapping("/api/profile/about")
	public ResponseEntity<ApiResponse<Map<String, String>>> showUserProfileAboutPage(Principal principal) {
		log.debug("API request: show user profile about data | user={}", principal.getName());
		return ResponseEntity.ok(ApiResponse.success(
				"Profile about fetched",
				Map.of(
						"email", principal.getName(),
						"message", "Authenticated user profile endpoint")));
	}
}
