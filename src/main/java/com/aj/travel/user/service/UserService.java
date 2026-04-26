package com.aj.travel.user.service;

import com.aj.travel.user.domain.User;
import com.aj.travel.user.domain.UserRole;
import com.aj.travel.user.dto.CreateUserRequest;
import com.aj.travel.user.dto.UserResponse;
import com.aj.travel.user.mapper.UserMapper;
import com.aj.travel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // 🔹 USER REGISTER
    public UserResponse register(CreateUserRequest request) {
        validateUniqueEmail(request.getEmail());

        User user = userMapper.toEntity(request);

        // 🔒 enforce role (prevent injection)
        user.setRole(UserRole.USER);

        // 🔒 encode password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        try {
            return userMapper.toResponse(userRepository.save(user));
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Email already registered");
        }
    }

    // 🔹 ADMIN REGISTER
    public UserResponse createAdmin(CreateUserRequest request) {
        validateUniqueEmail(request.getEmail());

        User user = userMapper.toEntity(request);

        // 🔒 enforce admin role
        user.setRole(UserRole.ADMIN);

        // 🔒 encode password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        try {
            return userMapper.toResponse(userRepository.save(user));
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Email already registered");
        }
    }

    // 🔹 GET USERS
    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    // 🔹 EMAIL VALIDATION
    private void validateUniqueEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }
    }
}