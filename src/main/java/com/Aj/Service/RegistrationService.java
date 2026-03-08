package com.Aj.Service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Aj.Entity.User;
import com.Aj.Entity.UserRole;
import com.Aj.Exception.BadRequestException;
import com.Aj.Exception.ResourceNotFoundException;
import com.Aj.Repository.UserRepository;

@Service
public class RegistrationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User register(User inputUser, UserRole role) {
		userRepository.findByEmail(inputUser.getEmail()).ifPresent(existing -> {
			throw new BadRequestException("Email already registered");
		});

		User user = new User();
		user.setUserName1(inputUser.getUserName1());
		user.setEmail(inputUser.getEmail());
		user.setUserPhoneNO(inputUser.getUserPhoneNO());
		user.setUserAddresh(inputUser.getUserAddresh());
		user.setUserPassword(passwordEncoder.encode(inputUser.getUserPassword()));
		user.setUserRole(role);
		return userRepository.save(user);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found for email: " + email));
	}

	public User findById(int id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
	}

	public void deleteUser(int id) {
		if (!userRepository.existsById(id)) {
			throw new ResourceNotFoundException("User not found: " + id);
		}
		userRepository.deleteById(id);
	}
}
