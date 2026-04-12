package com.aj.travel.user.service;

import com.aj.travel.common.exception.ResourceNotFoundException;
import com.aj.travel.user.domain.User;
import com.aj.travel.user.domain.UserRole;
import com.aj.travel.user.dto.CreateUserRequest;
import com.aj.travel.user.dto.UserResponse;
import com.aj.travel.user.mapper.UserMapper;
import com.aj.travel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(CreateUserRequest request) {
        validateUniqueEmail(request.getEmail());

        User user = userMapper.toEntity(request);
        user.setRole(UserRole.USER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponse createAdmin(CreateUserRequest request) {
        validateUniqueEmail(request.getEmail());

        User user = userMapper.toEntity(request);
        user.setRole(UserRole.ADMIN);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public java.util.List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    private void validateUniqueEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }
    }
}
