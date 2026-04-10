package com.aj.travel.controller;

import static com.aj.travel.constants.ApiPaths.ADMIN;
import static com.aj.travel.constants.ApiPaths.DASHBOARD;
import static com.aj.travel.constants.ApiPaths.USER_BY_ID;
import static com.aj.travel.constants.ApiPaths.USERS;
import static com.aj.travel.constants.SecurityConstants.HAS_ROLE_ADMIN;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aj.travel.dto.ApiResponse;
import com.aj.travel.entity.User;
import com.aj.travel.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
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

	@GetMapping({DASHBOARD, USERS})
	public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getAdminDashboard() {
		log.info("API request: load admin dashboard");
		List<User> users = userService.getAllUser();
		log.info("API response: admin dashboard loaded | userCount={}", users.size());
		return ResponseEntity.ok(ApiResponse.success(
				"Users fetched",
				users.stream().map(this::toUserPayload).toList()));
	}

	@PutMapping(USER_BY_ID)
	public ResponseEntity<ApiResponse<Map<String, Object>>> updateUser(
			@PathVariable int id,
			@Valid @RequestBody User updatedUser) {
		log.info("API request: update user | userId={}", id);
		User existingUser = userService.updateUser(id);

		existingUser.setUserId(id);
		existingUser.setUserName(updatedUser.getUserName());
		if (updatedUser.getUserPassword() != null && !updatedUser.getUserPassword().isBlank()) {
			existingUser.setUserPassword(passwordEncoder.encode(updatedUser.getUserPassword()));
		}
		existingUser.setEmail(updatedUser.getEmail());
		existingUser.setUserPhoneNo(updatedUser.getUserPhoneNo());

		User savedUser = userService.saveUser(existingUser);
		log.info("API response: user updated | userId={}", id);
		return ResponseEntity.ok(ApiResponse.success("User updated", toUserPayload(savedUser)));
	}

	@DeleteMapping(USER_BY_ID)
	public ResponseEntity<ApiResponse<Map<String, Object>>> deleteUser(@PathVariable int id) {
		log.info("API request: delete user | userId={}", id);
		userService.deleteUser(id);
		log.info("API response: user deleted | userId={}", id);
		return ResponseEntity.ok(ApiResponse.success(
				"User deleted",
				Map.of("userId", id, "deleted", true)));
	}

	private Map<String, Object> toUserPayload(User user) {
		return Map.of(
				"userId", user.getUserId(),
				"userName", user.getUserName(),
				"email", user.getEmail(),
				"userAddress", user.getUserAddress() == null ? "" : user.getUserAddress(),
				"userPhoneNo", user.getUserPhoneNo() == null ? "" : user.getUserPhoneNo(),
				"userRole", user.getUserRole().name());
	}
}
