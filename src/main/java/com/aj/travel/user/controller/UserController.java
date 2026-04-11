package com.aj.travel.user.controller;

import com.aj.travel.common.api.ApiResponse;
import com.aj.travel.user.dto.UserResponse;
import com.aj.travel.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "User retrieved successfully",
                userService.getUser(id)
        );
    }
}
