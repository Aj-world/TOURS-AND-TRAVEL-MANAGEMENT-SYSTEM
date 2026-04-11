package com.aj.travel.auth.controller;

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

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody CreateUserRequest request) {

        UserResponse savedUser = userService.register(request);

        return ResponseEntity.ok(savedUser);
    }

}
