package com.aj.travel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aj.travel.entity.User;
import com.aj.travel.exception.ResourceNotFoundException;
import com.aj.travel.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User saveUser(User user) {
		log.debug("Saving user entity | userId={} | email={}", user.getUserId(), user.getEmail());
		return userRepository.save(user);
	}

	public List<User> getAllUser() {
		log.debug("Fetching all user entities");
		return userRepository.findAll();
	}

	public User getUserby_id(int id) {
		log.debug("Fetching user by id | userId={}", id);
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
	}

	public User updateUser(int id) {
		log.debug("Loading user for update | userId={}", id);
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
	}

	public int deleteUser(int id) {
		log.info("Deleting user entity | userId={}", id);
		if (!userRepository.existsById(id)) {
			log.warn("Delete user entity rejected | userId={} | reason=not-found", id);
			throw new ResourceNotFoundException("User not found: " + id);
		}
		userRepository.deleteById(id);
		log.info("Deleted user entity | userId={}", id);
		return 1;
	}

}

