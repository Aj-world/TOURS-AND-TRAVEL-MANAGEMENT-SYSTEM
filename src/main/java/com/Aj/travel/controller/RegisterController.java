package com.aj.travel.controller;

import static com.aj.travel.constants.ApiPaths.ADMIN;
import static com.aj.travel.constants.ApiPaths.AUTH_REGISTER;
import static com.aj.travel.constants.ApiPaths.LOGIN;
import static com.aj.travel.constants.ApiPaths.LOGIN_SUCCESS;
import static com.aj.travel.constants.ApiPaths.REDIRECT_PREFIX;
import static com.aj.travel.constants.ApiPaths.REGISTER_ADMIN;
import static com.aj.travel.constants.ApiPaths.REGISTER_USER;
import static com.aj.travel.constants.SecurityConstants.HAS_ROLE_ADMIN;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aj.travel.entity.User;
import com.aj.travel.entity.UserRole;
import com.aj.travel.service.RegistrationService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(AUTH_REGISTER)
@Slf4j
public class RegisterController {

	private final RegistrationService registrationService;

	public RegisterController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@PreAuthorize(HAS_ROLE_ADMIN)
	@GetMapping(REGISTER_ADMIN)
	public String showAdminRegistrationPage() {
		return "/Authentication/Registion_Page_Admin";
	}

	@PreAuthorize(HAS_ROLE_ADMIN)
	@PostMapping(REGISTER_ADMIN)
	public String registerAdmin(@ModelAttribute User admin, Model model) {
		log.info("MVC request: register admin | email={}", admin.getEmail());
		User createdAdmin = registrationService.register(admin, UserRole.ADMIN);
		log.info("MVC response: admin registered | userId={} | email={}",
				createdAdmin.getUserId(), createdAdmin.getEmail());
		model.addAttribute("user", createdAdmin);
		return REDIRECT_PREFIX + ADMIN + LOGIN_SUCCESS;
	}

	@GetMapping(REGISTER_USER)
	public String showUserRegistrationPage() {
		return "/Authentication/Registion_Page_User";
	}

	@PostMapping(REGISTER_USER)
	public String registerUser(@ModelAttribute User user, Model model) {
		log.info("MVC request: register user | email={}", user.getEmail());
		User createdUser = registrationService.register(user, UserRole.USER);
		log.info("MVC response: user registered | userId={} | email={}",
				createdUser.getUserId(), createdUser.getEmail());
		model.addAttribute("user", createdUser);
		return REDIRECT_PREFIX + LOGIN;
	}
}
