package com.aj.travel.user.mapper;

import com.aj.travel.user.domain.User;
import com.aj.travel.user.dto.CreateUserRequest;
import com.aj.travel.user.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());

        // ❌ DO NOT MAP ROLE FROM REQUEST
        // user.setRole(request.getRole());

        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole() != null ? user.getRole().name() : null,
                user.getCreatedAt()
        );
    }
}