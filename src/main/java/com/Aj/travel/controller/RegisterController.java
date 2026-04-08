package com.Aj.travel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Aj.travel.Entity.User;
import com.Aj.travel.Entity.UserRole;
import com.Aj.travel.Service.RegistrationService;

@Controller
@RequestMapping("/auth/register")
public class RegisterController {

	private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

	private final RegistrationService registrationService;

	public RegisterController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String showAdminRegistrationPage() {
		return "/Authentication/Registion_Page_Admin";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin")
	public String registerAdmin(@ModelAttribute User admin, Model model) {
		User createdAdmin = registrationService.register(admin, UserRole.ADMIN);
		log.info("Registered admin account with id={} and email={}", createdAdmin.getUserId(), createdAdmin.getEmail());
		model.addAttribute("user", createdAdmin);
		return "redirect:/admin/login-success";
	}

	@GetMapping("/user")
	public String showUserRegistrationPage() {
		return "/Authentication/Registion_Page_User";
	}

	@PostMapping("/user")
	public String registerUser(@ModelAttribute User user, Model model) {
		User createdUser = registrationService.register(user, UserRole.USER);
		log.info("Registered user account with id={} and email={}", createdUser.getUserId(), createdUser.getEmail());
		model.addAttribute("user", createdUser);
		return "redirect:/login";
	}
}
