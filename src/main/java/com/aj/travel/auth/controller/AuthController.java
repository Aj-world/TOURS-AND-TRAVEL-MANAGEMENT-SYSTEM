package com.aj.travel.auth.controller;

import com.aj.travel.user.domain.User;
import com.aj.travel.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {

        User savedUser = userService.register(user);

        return ResponseEntity.ok(savedUser);
    }

}