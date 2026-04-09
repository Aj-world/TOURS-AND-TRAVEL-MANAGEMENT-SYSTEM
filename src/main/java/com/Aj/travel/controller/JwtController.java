package com.aj.travel.controller;

import static com.aj.travel.constants.ApiPaths.AUTH;
import static com.aj.travel.constants.ApiPaths.LOGIN;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aj.travel.dto.ApiResponse;
import com.aj.travel.dto.LoginRequest;
import com.aj.travel.service.JwtService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(AUTH)
public class JwtController {

	private static final Logger log = LoggerFactory.getLogger(JwtController.class);

	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public JwtController(JwtService jwtService, AuthenticationManager authenticationManager) {
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping(LOGIN)
	public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
			if (authentication.isAuthenticated()) {
				log.info("JWT login succeeded for email={}", loginRequest.getEmail());
				return ResponseEntity.ok(ApiResponse.success(
						"Login successful",
						jwtService.generateToken(loginRequest.getEmail())));
			}
			log.warn("JWT login rejected for email={}", loginRequest.getEmail());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Invalid credentials"));
		} catch (AuthenticationException ex) {
			log.warn("JWT login failed for email={}", loginRequest.getEmail());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Invalid credentials"));
		}
	}
}
