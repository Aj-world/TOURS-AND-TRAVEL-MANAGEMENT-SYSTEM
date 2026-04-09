package com.aj.travel.controller;

import static com.aj.travel.constants.ApiPaths.ABOUT;
import static com.aj.travel.constants.SecurityConstants.HAS_ROLE_USER;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(ABOUT)
@PreAuthorize(HAS_ROLE_USER)
@Slf4j
public class AboutController {

	@GetMapping
	public String showAboutPage() {
		log.debug("MVC request: show about page");
		return "/User/about_Page";
	}
}
