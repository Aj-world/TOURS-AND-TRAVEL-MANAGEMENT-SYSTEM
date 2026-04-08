package com.aj.travel.controller;

import static com.aj.travel.constants.ApiPaths.HOME;
import static com.aj.travel.constants.SecurityConstants.HAS_ROLE_USER;

import java.security.Principal;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aj.travel.entity.User;
import com.aj.travel.service.PackageService;
import com.aj.travel.service.RegistrationService;

@Controller
@RequestMapping(HOME)
@PreAuthorize(HAS_ROLE_USER)
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
		model.addAttribute("name", user.getUserName());
		model.addAttribute("packages", packageService.getAllPackages(PageRequest.of(0, 3)).getContent());
		return "/User/home_Page";
	}
}
