package com.aj.travel.booking.controller;

import com.aj.travel.booking.dto.BookingResponse;
import com.aj.travel.booking.service.BookingService;
import com.aj.travel.common.config.SecurityConfig;
import com.aj.travel.common.security.CustomUserDetailsService;
import com.aj.travel.common.security.JwtAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() throws Exception {
        doAnswer(invocation -> {
            jakarta.servlet.FilterChain filterChain = invocation.getArgument(2);
            jakarta.servlet.ServletRequest request = invocation.getArgument(0);
            jakarta.servlet.ServletResponse response = invocation.getArgument(1);
            filterChain.doFilter(request, response);
            return null;
        }).when(jwtAuthenticationFilter).doFilter(any(), any(), any());
    }

    @Test
    @WithMockUser(roles = "USER")
    void createBooking_success() throws Exception {
        // Arrange
        String requestJson = """
                {
                  "packageId":1,
                  "guestCount":2
                }
                """;

        BookingResponse response = new BookingResponse(
                1L,
                10L,
                1L,
                2,
                BigDecimal.valueOf(1000),
                "PENDING_PAYMENT",
                LocalDateTime.now()
        );

        when(bookingService.createBooking(any())).thenReturn(response);

        // Act Assert
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Booking created successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.packageId").value(1))
                .andExpect(jsonPath("$.data.status").value("PENDING_PAYMENT"));

        verify(bookingService).createBooking(any());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getMyBookings_success() throws Exception {
        // Arrange
        List<BookingResponse> responses = List.of(
                new BookingResponse(1L, 10L, 1L, 2, BigDecimal.valueOf(1000), "PENDING_PAYMENT", LocalDateTime.now()),
                new BookingResponse(2L, 10L, 2L, 3, BigDecimal.valueOf(2000), "CONFIRMED", LocalDateTime.now())
        );

        when(bookingService.getMyBookings()).thenReturn(responses);

        // Act Assert
        mockMvc.perform(get("/bookings/my"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Bookings retrieved successfully"))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[1].status").value("CONFIRMED"));

        verify(bookingService).getMyBookings();
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllBookings_adminOnly() throws Exception {
        // Arrange Act Assert
        mockMvc.perform(get("/bookings/all"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.error").value("FORBIDDEN"))
                .andExpect(jsonPath("$.message").value("Access denied"))
                .andExpect(jsonPath("$.path").value("/bookings/all"));

        verify(bookingService, never()).getAllBookings();
    }
}
