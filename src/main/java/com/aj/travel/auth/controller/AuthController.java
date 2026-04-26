package com.aj.travel.auth.controller;

import com.aj.travel.auth.dto.LoginRequest;
import com.aj.travel.auth.dto.LoginResponse;
import com.aj.travel.auth.service.AuthService;
import com.aj.travel.common.api.ApiResponse;
import com.aj.travel.user.dto.CreateUserRequest;
import com.aj.travel.user.dto.UserResponse;
import com.aj.travel.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication and user registration APIs")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    // 🔹 REGISTER
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user")
    public ApiResponse<UserResponse> register(
            @Valid @RequestBody CreateUserRequest request
    ) {

        UserResponse user = userService.register(request);

        return ApiResponse.success(
                "User registered successfully",
                user
        );
    }

    // 🔹 LOGIN
    @PostMapping("/login")
    @Operation(summary = "Authenticate user and return JWT token")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        LoginResponse response = authService.login(request);

        return ApiResponse.success(
                "Login successful",
                response
        );
    }
}