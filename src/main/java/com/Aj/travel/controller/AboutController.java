package com.aj.travel.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
@PreAuthorize("hasRole('USER')")
public class AboutController {

	@GetMapping
	public String showAboutPage() {
		return "/User/about_Page";
	}
}
