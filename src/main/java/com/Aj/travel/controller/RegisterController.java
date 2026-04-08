package com.aj.travel.controller;

import static com.aj.travel.constants.ApiPaths.ADMIN;
import static com.aj.travel.constants.ApiPaths.AUTH_REGISTER;
import static com.aj.travel.constants.ApiPaths.LOGIN;
import static com.aj.travel.constants.ApiPaths.LOGIN_SUCCESS;
import static com.aj.travel.constants.ApiPaths.REDIRECT_PREFIX;
import static com.aj.travel.constants.ApiPaths.REGISTER_ADMIN;
import static com.aj.travel.constants.ApiPaths.REGISTER_USER;
import static com.aj.travel.constants.SecurityConstants.HAS_ROLE_ADMIN;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
@RequestMapping(AUTH_REGISTER)
public class RegisterController {

	private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

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
		User createdAdmin = registrationService.register(admin, UserRole.ADMIN);
		log.info("Registered admin account with id={} and email={}", createdAdmin.getUserId(), createdAdmin.getEmail());
		model.addAttribute("user", createdAdmin);
		return REDIRECT_PREFIX + ADMIN + LOGIN_SUCCESS;
	}

	@GetMapping(REGISTER_USER)
	public String showUserRegistrationPage() {
		return "/Authentication/Registion_Page_User";
	}

	@PostMapping(REGISTER_USER)
	public String registerUser(@ModelAttribute User user, Model model) {
		User createdUser = registrationService.register(user, UserRole.USER);
		log.info("Registered user account with id={} and email={}", createdUser.getUserId(), createdUser.getEmail());
		model.addAttribute("user", createdUser);
		return REDIRECT_PREFIX + LOGIN;
	}
}
