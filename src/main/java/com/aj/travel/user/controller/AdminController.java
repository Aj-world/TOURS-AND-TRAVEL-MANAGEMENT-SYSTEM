package com.aj.travel.user.controller;

import com.aj.travel.common.api.ApiResponse;
import com.aj.travel.user.dto.CreateUserRequest;
import com.aj.travel.user.dto.UserResponse;
import com.aj.travel.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PostMapping("/admin/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerAdmin(@Valid @RequestBody CreateUserRequest request) {
        UserResponse savedAdmin = userService.createAdmin(request);

        return ResponseEntity.ok(ApiResponse.success(
                "Admin registered successfully",
                savedAdmin
        ));
    }
}
