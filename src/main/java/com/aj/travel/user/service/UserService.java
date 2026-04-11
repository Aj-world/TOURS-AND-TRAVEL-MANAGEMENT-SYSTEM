package com.aj.travel.user.service;

import com.aj.travel.common.exception.ResourceNotFoundException;
import com.aj.travel.user.domain.User;
import com.aj.travel.user.dto.CreateUserRequest;
import com.aj.travel.user.dto.UserResponse;
import com.aj.travel.user.mapper.UserMapper;
import com.aj.travel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse register(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = userMapper.toEntity(request);

        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return userMapper.toResponse(user);
    }
}
