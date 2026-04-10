package com.aj.travel.controller;

import static com.aj.travel.constants.ApiPaths.ADMIN;
import static com.aj.travel.constants.ApiPaths.AUTH_REGISTER;
import static com.aj.travel.constants.ApiPaths.REGISTER_ADMIN;
import static com.aj.travel.constants.ApiPaths.REGISTER_USER;
import static com.aj.travel.constants.SecurityConstants.HAS_ROLE_ADMIN;

import java.net.URI;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aj.travel.dto.ApiResponse;
import com.aj.travel.entity.User;
import com.aj.travel.entity.UserRole;
import com.aj.travel.service.RegistrationService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(AUTH_REGISTER)
@Slf4j
public class RegisterController {

	private final RegistrationService registrationService;

	public RegisterController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@PreAuthorize(HAS_ROLE_ADMIN)
	@PostMapping(REGISTER_ADMIN)
	public ResponseEntity<ApiResponse<Map<String, Object>>> registerAdmin(@Valid @RequestBody User admin) {
		log.info("API request: register admin | email={}", admin.getEmail());
		User createdAdmin = registrationService.register(admin, UserRole.ADMIN);
		log.info("API response: admin registered | userId={} | email={}",
				createdAdmin.getUserId(), createdAdmin.getEmail());
		return ResponseEntity.created(URI.create(ADMIN + "/users/" + createdAdmin.getUserId()))
				.body(ApiResponse.success("Admin registered", toUserPayload(createdAdmin)));
	}

	@PostMapping(REGISTER_USER)
	public ResponseEntity<ApiResponse<Map<String, Object>>> registerUser(@Valid @RequestBody User user) {
		log.info("API request: register user | email={}", user.getEmail());
		User createdUser = registrationService.register(user, UserRole.USER);
		log.info("API response: user registered | userId={} | email={}",
				createdUser.getUserId(), createdUser.getEmail());
		return ResponseEntity.created(URI.create(AUTH_REGISTER + REGISTER_USER + "/" + createdUser.getUserId()))
				.body(ApiResponse.success("User registered", toUserPayload(createdUser)));
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
