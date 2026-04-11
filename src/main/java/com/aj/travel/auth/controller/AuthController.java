package com.aj.travel.auth.controller;

import com.aj.travel.auth.dto.LoginRequest;
import com.aj.travel.auth.dto.LoginResponse;
import com.aj.travel.auth.service.AuthService;
import com.aj.travel.common.api.ApiResponse;
import com.aj.travel.user.dto.CreateUserRequest;
import com.aj.travel.user.dto.UserResponse;
import com.aj.travel.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody CreateUserRequest request) {

        UserResponse savedUser = userService.register(request);

        return ResponseEntity.ok(ApiResponse.success(
                "User registered successfully",
                savedUser
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(ApiResponse.success(
                "Login successful",
                response
        ));
    }

}
