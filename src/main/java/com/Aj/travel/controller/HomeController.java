package com.Aj.travel.controller;

import java.security.Principal;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Aj.travel.Entity.User;
import com.Aj.travel.Service.PackageService;
import com.Aj.travel.Service.RegistrationService;

@Controller
@RequestMapping("/home")
@PreAuthorize("hasRole('USER')")
public class HomeController {

	private final RegistrationService registrationService;
	private final PackageService packageService;

	public HomeController(RegistrationService registrationService, PackageService packageService) {
		this.registrationService = registrationService;
		this.packageService = packageService;
	}

	@GetMapping
	public String showHomePage(Principal principal, Model model) {
		User user = registrationService.findByEmail(principal.getName());
		model.addAttribute("name", user.getUserName1());
		model.addAttribute("packages", packageService.getAllPackages(PageRequest.of(0, 3)).getContent());
		return "/User/home_Page";
	}
}
