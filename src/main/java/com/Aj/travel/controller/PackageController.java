package com.aj.travel.controller;

import static com.aj.travel.constants.ApiPaths.PACKAGES;
import static com.aj.travel.constants.ApiPaths.PACKAGE_BY_ID;
import static com.aj.travel.constants.SecurityConstants.HAS_ROLE_USER;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aj.travel.entity.Package;
import com.aj.travel.service.PackageService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(PACKAGES)
@PreAuthorize(HAS_ROLE_USER)
@Slf4j
public class PackageController {

	private final PackageService packageService;

	public PackageController(PackageService packageService) {
		this.packageService = packageService;
	}

	@GetMapping
	public String listPackages(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			Model model) {
		log.info("MVC request: list packages | page={} | size={}", page, size);
		Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1));
		Page<Package> packagePage = packageService.getAllPackages(pageable);
		model.addAttribute("packagePage", packagePage);
		model.addAttribute("packages", packagePage.getContent());
		model.addAttribute("currentPage", packagePage.getNumber());
		model.addAttribute("pageSize", packagePage.getSize());
		log.info("MVC response: packages listed | page={} | returned={}",
				packagePage.getNumber(), packagePage.getNumberOfElements());
		return "/User/Package_Page";
	}

	@GetMapping(PACKAGE_BY_ID)
	public String getPackageDetails(@PathVariable int id, Model model) {
		log.info("MVC request: package details | packageId={}", id);
		model.addAttribute("travelPackage", packageService.getPackageById(id));
		log.debug("MVC response: package details loaded | packageId={}", id);
		return "/User/Book_Page";
	}
}
