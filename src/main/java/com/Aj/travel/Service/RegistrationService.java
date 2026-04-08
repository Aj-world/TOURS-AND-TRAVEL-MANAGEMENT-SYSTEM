package com.aj.travel.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.travel.entity.User;
import com.aj.travel.entity.UserRole;
import com.aj.travel.exception.BadRequestException;
import com.aj.travel.exception.ResourceNotFoundException;
import com.aj.travel.repository.UserRepository;

@Service
public class RegistrationService {

	private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public User register(User inputUser, UserRole role) {
		userRepository.findByEmail(inputUser.getEmail()).ifPresent(existing -> {
			log.warn("Rejected registration attempt for email={} because it is already registered",
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
		log.info("Registered user id={} email={} role={}",
				savedUser.getUserId(), savedUser.getEmail(), savedUser.getUserRole());
		return savedUser;
	}

	@Transactional
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Transactional(readOnly = true)
	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found for email: " + email));
	}

	@Transactional(readOnly = true)
	public User findById(int id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
	}

	@Transactional
	public void deleteUser(int id) {
		if (!userRepository.existsById(id)) {
			throw new ResourceNotFoundException("User not found: " + id);
		}
		userRepository.deleteById(id);
	}
}
