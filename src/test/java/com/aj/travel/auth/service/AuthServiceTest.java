package com.aj.travel.auth.service;

import com.aj.travel.auth.dto.LoginRequest;
import com.aj.travel.auth.dto.LoginResponse;
import com.aj.travel.common.security.JwtTokenProvider;
import com.aj.travel.user.domain.User;
import com.aj.travel.user.domain.UserRole;
import com.aj.travel.user.dto.CreateUserRequest;
import com.aj.travel.user.dto.UserResponse;
import com.aj.travel.user.mapper.UserMapper;
import com.aj.travel.user.repository.UserRepository;
import com.aj.travel.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser_success() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest(
                "John Doe",
                "john@example.com",
                "plain-password",
                "9999999999",
                null
        );

        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPhone("9999999999");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("John Doe");
        savedUser.setEmail("john@example.com");
        savedUser.setPhone("9999999999");
        savedUser.setRole(UserRole.USER);
        savedUser.setCreatedAt(LocalDateTime.now());

        UserResponse expectedResponse = new UserResponse(
                1L,
                "John Doe",
                "john@example.com",
                "9999999999",
                "USER",
                savedUser.getCreatedAt()
        );

        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(userMapper.toEntity(request)).thenReturn(user);
        when(passwordEncoder.encode("plain-password")).thenReturn("encoded-password");
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toResponse(savedUser)).thenReturn(expectedResponse);

        // Act
        UserResponse response = userService.register(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("USER", response.getRole());
        assertEquals(UserRole.USER, user.getRole());
        assertEquals("encoded-password", user.getPassword());
        verify(userRepository).existsByEmail("john@example.com");
        verify(userMapper).toEntity(request);
        verify(passwordEncoder).encode("plain-password");
        verify(userRepository).save(user);
        verify(userMapper).toResponse(savedUser);
    }

    @Test
    void createAdmin_success() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest(
                "Admin User",
                "admin@example.com",
                "admin-password",
                "8888888888",
                null
        );

        User user = new User();
        user.setName("Admin User");
        user.setEmail("admin@example.com");
        user.setPhone("8888888888");

        User savedUser = new User();
        savedUser.setId(2L);
        savedUser.setName("Admin User");
        savedUser.setEmail("admin@example.com");
        savedUser.setPhone("8888888888");
        savedUser.setRole(UserRole.ADMIN);
        savedUser.setCreatedAt(LocalDateTime.now());

        UserResponse expectedResponse = new UserResponse(
                2L,
                "Admin User",
                "admin@example.com",
                "8888888888",
                "ADMIN",
                savedUser.getCreatedAt()
        );

        when(userRepository.existsByEmail("admin@example.com")).thenReturn(false);
        when(userMapper.toEntity(request)).thenReturn(user);
        when(passwordEncoder.encode("admin-password")).thenReturn("encoded-admin-password");
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toResponse(savedUser)).thenReturn(expectedResponse);

        // Act
        UserResponse response = userService.createAdmin(request);

        // Assert
        assertNotNull(response);
        assertEquals(2L, response.getId());
        assertEquals("ADMIN", response.getRole());
        assertEquals(UserRole.ADMIN, user.getRole());
        assertEquals("encoded-admin-password", user.getPassword());
        verify(userRepository).existsByEmail("admin@example.com");
        verify(userMapper).toEntity(request);
        verify(passwordEncoder).encode("admin-password");
        verify(userRepository).save(user);
        verify(userMapper).toResponse(savedUser);
    }

    @Test
    void login_success() {
        // Arrange
        LoginRequest request = new LoginRequest("john@example.com", "password");
        Authentication authentication =
                new UsernamePasswordAuthenticationToken("john@example.com", null);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtTokenProvider.generateToken("john@example.com")).thenReturn("jwt-token");

        // Act
        LoginResponse response = authService.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).generateToken("john@example.com");
    }

    @Test
    void login_invalidPassword() {
        // Arrange
        LoginRequest request = new LoginRequest("john@example.com", "wrong-password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act
        BadCredentialsException exception = assertThrows(
                BadCredentialsException.class,
                () -> authService.login(request)
        );

        // Assert
        assertEquals("Bad credentials", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider, never()).generateToken(any());
    }

    @Test
    void login_userNotFound() {
        // Arrange
        LoginRequest request = new LoginRequest("missing@example.com", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new UsernameNotFoundException("User not found"));

        // Act
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> authService.login(request)
        );

        // Assert
        assertEquals("User not found", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider, never()).generateToken(any());
    }
}
