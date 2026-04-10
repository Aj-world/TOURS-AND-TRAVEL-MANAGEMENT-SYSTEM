package com.aj.travel.controller;

import static com.aj.travel.constants.SecurityConstants.HAS_ROLE_USER;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aj.travel.dto.ApiResponse;
import com.aj.travel.entity.User;
import com.aj.travel.service.PackageService;
import com.aj.travel.service.RegistrationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/home")
@PreAuthorize(HAS_ROLE_USER)
@Slf4j
public class HomeController {

	private final RegistrationService registrationService;
	private final PackageService packageService;

	public HomeController(RegistrationService registrationService, PackageService packageService) {
		this.registrationService = registrationService;
		this.packageService = packageService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Map<String, Object>>> showHomePage(Principal principal) {
		log.info("API request: load home data | user={}", principal.getName());
		User user = registrationService.findByEmail(principal.getName());
		List<Map<String, Object>> packages = packageService.getAllPackages(PageRequest.of(0, 3))
				.getContent()
				.stream()
				.map(this::toPackagePayload)
				.toList();
		log.debug("API response: home data loaded | user={} | featuredPackages=3", principal.getName());
		return ResponseEntity.ok(ApiResponse.success(
				"Home data fetched",
				Map.of(
						"name", user.getUserName(),
						"packages", packages)));
	}

	private Map<String, Object> toPackagePayload(com.aj.travel.entity.Package travelPackage) {
		Map<String, Object> payload = new LinkedHashMap<>();
		payload.put("packageId", travelPackage.getPackageId());
		payload.put("packageName", travelPackage.getPackageName());
		payload.put("packageType", travelPackage.getPackageType());
		payload.put("journeyDate", travelPackage.getJourneyDate());
		payload.put("price", travelPackage.getPrice());
		return payload;
	}
}
