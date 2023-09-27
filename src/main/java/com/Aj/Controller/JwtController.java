package com.Aj.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Aj.Service.JwtService;

import DTO.User;

@RestController
class JwtController {

	@Autowired
	JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;

	@GetMapping("/Aj11")
	public ResponseEntity<String> h1(@RequestBody User user) {

		System.out.println(user.getEmail());

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassWord()));

		if (authentication.isAuthenticated()) {

			return ResponseEntity.ok(jwtService.generateToken(user.getEmail()));
		}

		else {

			throw new UsernameNotFoundException("invalide user");
		}

	}

}
