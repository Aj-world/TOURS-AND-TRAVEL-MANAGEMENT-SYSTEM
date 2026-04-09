package com.aj.travel.controller;

import static com.aj.travel.constants.ApiPaths.ADMIN;
import static com.aj.travel.constants.ApiPaths.DASHBOARD;
import static com.aj.travel.constants.ApiPaths.LOGIN_SUCCESS;
import static com.aj.travel.constants.ApiPaths.REDIRECT_PREFIX;
import static com.aj.travel.constants.ApiPaths.USER_BY_ID;
import static com.aj.travel.constants.ApiPaths.USER_DELETE;
import static com.aj.travel.constants.ApiPaths.USER_EDIT;
import static com.aj.travel.constants.ApiPaths.USERS;
import static com.aj.travel.constants.SecurityConstants.HAS_ROLE_ADMIN;

import java.util.List;

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

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(ADMIN)
@PreAuthorize(HAS_ROLE_ADMIN)
@Slf4j
public class AdminController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping(LOGIN_SUCCESS)
	public String showAdminLoginSuccessPage() {
		log.debug("MVC response: admin login success page");
		return "/Admin/LoginSuccess";
	}

	@GetMapping({DASHBOARD, USERS})
	public String getAdminDashboard(Model model) {
		log.info("MVC request: load admin dashboard");
		List<User> users = userService.getAllUser();
		model.addAttribute("user", users);
		log.info("MVC response: admin dashboard loaded | userCount={}", users.size());
		return "/Admin/AdminDasBord";
	}

	@GetMapping(USER_EDIT)
	public String showUpdateUserForm(@PathVariable int id, Model model) {
		log.info("MVC request: show update user form | userId={}", id);
		model.addAttribute("id", id);
		return "/Admin/UpdateAdmin";
	}

	@PutMapping(USER_BY_ID)
	public String updateUser(@PathVariable int id, @ModelAttribute User updatedUser) {
		log.info("MVC request: update user | userId={}", id);
		User existingUser = userService.updateUser(id);

		existingUser.setUserId(id);
		existingUser.setUserName(updatedUser.getUserName());
		if (updatedUser.getUserPassword() != null && !updatedUser.getUserPassword().isBlank()) {
			existingUser.setUserPassword(passwordEncoder.encode(updatedUser.getUserPassword()));
		}
		existingUser.setEmail(updatedUser.getEmail());
		existingUser.setUserPhoneNo(updatedUser.getUserPhoneNo());

		userService.saveUser(existingUser);
		log.info("MVC response: user updated | userId={}", id);
		return REDIRECT_PREFIX + ADMIN + DASHBOARD;
	}

	@PostMapping(USER_BY_ID)
	public String updateUserFromForm(@PathVariable int id, @ModelAttribute User updatedUser) {
		return updateUser(id, updatedUser);
	}

	@DeleteMapping(USER_BY_ID)
	public String deleteUser(@PathVariable int id) {
		log.info("MVC request: delete user | userId={}", id);
		userService.deleteUser(id);
		log.info("MVC response: user deleted | userId={}", id);
		return REDIRECT_PREFIX + ADMIN + DASHBOARD;
	}

	@PostMapping(USER_DELETE)
	public String deleteUserFromForm(@PathVariable int id) {
		return deleteUser(id);
	}
}
