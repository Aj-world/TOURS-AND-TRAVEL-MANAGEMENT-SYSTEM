package com.aj.travel.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testUserRegistration() throws Exception {
        Map<String, Object> request = new LinkedHashMap<>();
        request.put("name", "Test User");
        request.put("email", "user@test.com");
        request.put("password", "Password@123");
        request.put("phone", "9876543210");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.role").value("USER"))
                .andExpect(jsonPath("$.data.email").value("user@test.com"))
                .andExpect(jsonPath("$.data.id").isNumber());
    }

    @Test
    void testAdminRegistration() throws Exception {
        Map<String, Object> request = new LinkedHashMap<>();
        request.put("name", "Admin User");
        request.put("email", "admin@test.com");
        request.put("password", "Admin@123");
        request.put("phone", "9999999999");

        mockMvc.perform(post("/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.role").value("ADMIN"))
                .andExpect(jsonPath("$.data.email").value("admin@test.com"))
                .andExpect(jsonPath("$.data.id").isNumber());
    }

    @Test
    void testUserLogin() throws Exception {
        Map<String, Object> registerRequest = new LinkedHashMap<>();
        registerRequest.put("name", "Login User");
        registerRequest.put("email", "login-user@test.com");
        registerRequest.put("password", "Password@123");
        registerRequest.put("phone", "9876543211");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.role").value("USER"));

        Map<String, Object> loginRequest = new LinkedHashMap<>();
        loginRequest.put("email", "login-user@test.com");
        loginRequest.put("password", "Password@123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").isString())
                .andExpect(jsonPath("$.data.token").isNotEmpty());
    }

    @Test
    void testAdminLogin() throws Exception {
        Map<String, Object> registerRequest = new LinkedHashMap<>();
        registerRequest.put("name", "Login Admin");
        registerRequest.put("email", "login-admin@test.com");
        registerRequest.put("password", "Admin@123");
        registerRequest.put("phone", "9999999998");

        mockMvc.perform(post("/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.role").value("ADMIN"));

        Map<String, Object> loginRequest = new LinkedHashMap<>();
        loginRequest.put("email", "login-admin@test.com");
        loginRequest.put("password", "Admin@123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").isString())
                .andExpect(jsonPath("$.data.token").isNotEmpty());
    }
}
