package com.aj.travel.user.controller;

import com.aj.travel.common.api.ApiResponse;
import com.aj.travel.user.dto.UserResponse;
import com.aj.travel.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @Secured("ROLE_ADMIN")
    public ApiResponse<List<UserResponse>> getUsers() {

        return new ApiResponse<>(
                true,
                "Users retrieved successfully",
                userService.getUsers()
        );
    }
}
