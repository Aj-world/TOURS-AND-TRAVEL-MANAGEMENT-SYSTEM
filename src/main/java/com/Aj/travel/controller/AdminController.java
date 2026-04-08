package com.aj.travel.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aj.travel.entity.User;
import com.aj.travel.service.UserService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

	private static final Logger log = LoggerFactory.getLogger(AdminController.class);

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/login-success")
	public String showAdminLoginSuccessPage() {
		return "/Admin/LoginSuccess";
	}

	@GetMapping({"/dashboard", "/users"})
	public String getAdminDashboard(Model model) {
		List<User> users = userService.getAllUser();
		model.addAttribute("user", users);
		return "/Admin/AdminDasBord";
	}

	@GetMapping("/users/{id}/edit")
	public String showUpdateUserForm(@PathVariable int id, Model model) {
		model.addAttribute("id", id);
		return "/Admin/UpdateAdmin";
	}

	@PutMapping("/users/{id}")
	public String updateUser(@PathVariable int id, @ModelAttribute User updatedUser) {
		log.info("Updating user with id={}", id);
		User existingUser = userService.updateUser(id);

		existingUser.setUserId(id);
		existingUser.setUserName(updatedUser.getUserName());
		if (updatedUser.getUserPassword() != null && !updatedUser.getUserPassword().isBlank()) {
			existingUser.setUserPassword(passwordEncoder.encode(updatedUser.getUserPassword()));
		}
		existingUser.setEmail(updatedUser.getEmail());
		existingUser.setUserPhoneNo(updatedUser.getUserPhoneNo());

		userService.saveUser(existingUser);
		return "redirect:/admin/dashboard";
	}

	@PostMapping("/users/{id}")
	public String updateUserFromForm(@PathVariable int id, @ModelAttribute User updatedUser) {
		return updateUser(id, updatedUser);
	}

	@DeleteMapping("/users/{id}")
	public String deleteUser(@PathVariable int id) {
		log.info("Deleting user with id={}", id);
		userService.deleteUser(id);
		return "redirect:/admin/dashboard";
	}

	@PostMapping("/users/{id}/delete")
	public String deleteUserFromForm(@PathVariable int id) {
		return deleteUser(id);
	}
}
