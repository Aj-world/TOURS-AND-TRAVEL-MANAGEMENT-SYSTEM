package com.aj.travel.controller;

import static com.aj.travel.constants.ApiPaths.HOME;
import static com.aj.travel.constants.ApiPaths.LOGIN;
import static com.aj.travel.constants.ApiPaths.PROFILE_ABOUT;
import static com.aj.travel.constants.ApiPaths.REDIRECT_PREFIX;
import static com.aj.travel.constants.ApiPaths.ROOT;
import static com.aj.travel.constants.SecurityConstants.HAS_ROLE_USER;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping
@Slf4j
public class LoginController {

	@GetMapping(LOGIN)
	public String showLoginPage() {
		log.debug("MVC request: show login page");
		return "/Authentication/Login_Page";
	}

	@GetMapping(ROOT)
	public String redirectToHomePage() {
		log.debug("MVC request: redirect root to home");
		return REDIRECT_PREFIX + HOME;
	}

	@PreAuthorize(HAS_ROLE_USER)
	@GetMapping(PROFILE_ABOUT)
	public String showUserProfileAboutPage() {
		log.debug("MVC request: show user profile about page");
		return "/User/User_about_Page";
	}
}
