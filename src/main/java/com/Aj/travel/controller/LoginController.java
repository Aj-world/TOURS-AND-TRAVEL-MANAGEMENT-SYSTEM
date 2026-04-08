package com.aj.travel.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoginController {

	@GetMapping("/login")
	public String showLoginPage() {
		return "/Authentication/Login_Page";
	}

	@GetMapping("/")
	public String redirectToHomePage() {
		return "redirect:/home";
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/profile/about")
	public String showUserProfileAboutPage() {
		return "/User/User_about_Page";
	}
}
