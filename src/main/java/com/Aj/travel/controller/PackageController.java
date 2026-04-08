package com.aj.travel.controller;

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

@Controller
@RequestMapping("/packages")
@PreAuthorize("hasRole('USER')")
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
		Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1));
		Page<Package> packagePage = packageService.getAllPackages(pageable);
		model.addAttribute("packagePage", packagePage);
		model.addAttribute("packages", packagePage.getContent());
		model.addAttribute("currentPage", packagePage.getNumber());
		model.addAttribute("pageSize", packagePage.getSize());
		return "/User/Package_Page";
	}

	@GetMapping("/{id}")
	public String getPackageDetails(@PathVariable int id, Model model) {
		model.addAttribute("travelPackage", packageService.getPackageById(id));
		return "/User/Book_Page";
	}
}
