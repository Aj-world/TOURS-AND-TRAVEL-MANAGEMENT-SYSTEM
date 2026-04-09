package com.aj.travel.controller;

import static com.aj.travel.constants.ApiPaths.AUTH;
import static com.aj.travel.constants.ApiPaths.LOGIN;

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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(AUTH)
@Slf4j
public class JwtController {

	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public JwtController(JwtService jwtService, AuthenticationManager authenticationManager) {
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping(LOGIN)
	public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest loginRequest) {
		log.info("API request: login | email={}", loginRequest.getEmail());
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
			if (authentication.isAuthenticated()) {
				log.info("API response: login succeeded | email={}", loginRequest.getEmail());
				return ResponseEntity.ok(ApiResponse.success(
						"Login successful",
						jwtService.generateToken(loginRequest.getEmail())));
			}
			log.warn("API response: login rejected | email={}", loginRequest.getEmail());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Invalid credentials"));
		} catch (AuthenticationException ex) {
			log.error("API response: login failed | email={} | error={}",
					loginRequest.getEmail(), ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Invalid credentials"));
		}
	}
}
