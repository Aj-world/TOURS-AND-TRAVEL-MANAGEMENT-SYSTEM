package com.aj.travel.user.controller;

import com.aj.travel.common.api.ApiResponse;
import com.aj.travel.user.dto.CreateUserRequest;
import com.aj.travel.user.dto.UserResponse;
import com.aj.travel.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> registerAdmin(
            @Valid @RequestBody CreateUserRequest request
    ) {

        UserResponse savedAdmin = userService.createAdmin(request);

        return ApiResponse.success(
                "Admin registered successfully",
                savedAdmin
        );
    }
}