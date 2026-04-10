package com.aj.travel.controller;

import static com.aj.travel.constants.ApiPaths.PACKAGE_BY_ID;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aj.travel.dto.ApiResponse;
import com.aj.travel.entity.Package;
import com.aj.travel.service.PackageService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/packages")
@Slf4j
public class PackageController {

	private final PackageService packageService;

	public PackageController(PackageService packageService) {
		this.packageService = packageService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Map<String, Object>>> listPackages(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		log.info("API request: list packages | page={} | size={}", page, size);
		Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1));
		Page<Package> packagePage = packageService.getAllPackages(pageable);
		List<Map<String, Object>> packages = packagePage.getContent().stream()
				.map(this::toPackagePayload)
				.toList();
		log.info("API response: packages listed | page={} | returned={}",
				packagePage.getNumber(), packagePage.getNumberOfElements());
		return ResponseEntity.ok(ApiResponse.success(
				"Packages fetched",
				Map.of(
						"packages", packages,
						"currentPage", packagePage.getNumber(),
						"pageSize", packagePage.getSize(),
						"totalElements", packagePage.getTotalElements(),
						"totalPages", packagePage.getTotalPages())));
	}

	@GetMapping(PACKAGE_BY_ID)
	public ResponseEntity<ApiResponse<Map<String, Object>>> getPackageDetails(@PathVariable int id) {
		log.info("API request: package details | packageId={}", id);
		Map<String, Object> payload = toPackagePayload(packageService.getPackageById(id));
		log.debug("API response: package details loaded | packageId={}", id);
		return ResponseEntity.ok(ApiResponse.success("Package fetched", payload));
	}

	private Map<String, Object> toPackagePayload(Package travelPackage) {
		Map<String, Object> payload = new LinkedHashMap<>();
		payload.put("packageId", travelPackage.getPackageId());
		payload.put("packageName", travelPackage.getPackageName());
		payload.put("packageType", travelPackage.getPackageType());
		payload.put("journeyDate", travelPackage.getJourneyDate());
		payload.put("price", travelPackage.getPrice());
		return payload;
	}
}
