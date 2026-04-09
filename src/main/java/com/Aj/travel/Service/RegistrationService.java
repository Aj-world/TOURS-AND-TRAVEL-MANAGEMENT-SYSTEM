package com.aj.travel.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.travel.entity.User;
import com.aj.travel.entity.UserRole;
import com.aj.travel.exception.BadRequestException;
import com.aj.travel.exception.ResourceNotFoundException;
import com.aj.travel.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RegistrationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public User register(User inputUser, UserRole role) {
		log.info("Registration started | email={} | role={}", inputUser.getEmail(), role);
		userRepository.findByEmail(inputUser.getEmail()).ifPresent(existing -> {
			log.warn("Registration rejected | email={} | reason=email-already-registered",
					inputUser.getEmail());
			throw new BadRequestException("Email already registered");
		});

		User user = new User();
		user.setUserName(inputUser.getUserName());
		user.setEmail(inputUser.getEmail());
		user.setUserPhoneNo(inputUser.getUserPhoneNo());
		user.setUserAddress(inputUser.getUserAddress());
		user.setUserPassword(passwordEncoder.encode(inputUser.getUserPassword()));
		user.setUserRole(role);
		User savedUser = userRepository.save(user);
		log.info("Registration completed | userId={} | email={} | role={}",
				savedUser.getUserId(), savedUser.getEmail(), savedUser.getUserRole());
		return savedUser;
	}

	@Transactional
	public User saveUser(User user) {
		log.debug("Saving user | userId={} | email={}", user.getUserId(), user.getEmail());
		return userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		log.debug("Fetching all users");
		return userRepository.findAll();
	}

	@Transactional(readOnly = true)
	public User findByEmail(String email) {
		log.debug("Finding user by email | email={}", email);
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found for email: " + email));
	}

	@Transactional(readOnly = true)
	public User findById(int id) {
		log.debug("Finding user by id | userId={}", id);
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
	}

	@Transactional
	public void deleteUser(int id) {
		log.info("Deleting user | userId={}", id);
		if (!userRepository.existsById(id)) {
			log.warn("Delete user rejected | userId={} | reason=not-found", id);
			throw new ResourceNotFoundException("User not found: " + id);
		}
		userRepository.deleteById(id);
		log.info("User deleted | userId={}", id);
	}
}
