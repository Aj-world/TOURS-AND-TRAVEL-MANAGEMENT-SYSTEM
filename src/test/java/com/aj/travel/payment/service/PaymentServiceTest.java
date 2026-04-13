package com.aj.travel.payment.service;

import com.aj.travel.booking.domain.Booking;
import com.aj.travel.booking.domain.BookingStatus;
import com.aj.travel.booking.repository.BookingRepository;
import com.aj.travel.common.exception.ResourceNotFoundException;
import com.aj.travel.common.security.AuthenticatedUserPrincipal;
import com.aj.travel.payment.domain.Payment;
import com.aj.travel.payment.domain.PaymentStatus;
import com.aj.travel.payment.dto.CreatePaymentRequest;
import com.aj.travel.payment.dto.PaymentResponse;
import com.aj.travel.payment.mapper.PaymentMapper;
import com.aj.travel.payment.repository.PaymentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentService paymentService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void createPayment_success() {
        // Arrange
        setAuthenticatedUser(5L);

        CreatePaymentRequest request = new CreatePaymentRequest(1L, "CARD");

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(5L);
        booking.setTotalPrice(BigDecimal.valueOf(2500));

        Payment payment = new Payment();
        payment.setBookingId(1L);
        payment.setAmount(BigDecimal.valueOf(2500));
        payment.setPaymentMethod("CARD");

        Payment savedPayment = new Payment();
        savedPayment.setId(20L);
        savedPayment.setBookingId(1L);
        savedPayment.setAmount(BigDecimal.valueOf(2500));
        savedPayment.setPaymentMethod("CARD");
        savedPayment.setStatus(PaymentStatus.CREATED);
        savedPayment.setPaidAt(LocalDateTime.now());

        PaymentResponse expectedResponse = new PaymentResponse(
                20L,
                1L,
                BigDecimal.valueOf(2500),
                "CARD",
                null,
                null,
                "CREATED",
                savedPayment.getPaidAt()
        );

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(paymentMapper.toEntity(request, booking)).thenReturn(payment);
        when(paymentRepository.save(payment)).thenReturn(savedPayment);
        when(paymentMapper.toResponse(savedPayment)).thenReturn(expectedResponse);

        // Act
        PaymentResponse response = paymentService.createPayment(request);

        // Assert
        assertNotNull(response);
        assertEquals(20L, response.getId());
        assertEquals("CREATED", response.getStatus());
        assertEquals(PaymentStatus.CREATED, payment.getStatus());
        verify(bookingRepository).findById(1L);
        verify(paymentMapper).toEntity(request, booking);
        verify(paymentRepository).save(payment);
        verify(paymentMapper).toResponse(savedPayment);
    }

    @Test
    void createPayment_bookingNotFound() {
        // Arrange
        setAuthenticatedUser(5L);

        CreatePaymentRequest request = new CreatePaymentRequest(100L, "UPI");
        when(bookingRepository.findById(100L)).thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> paymentService.createPayment(request)
        );

        // Assert
        assertEquals("Booking not found", exception.getMessage());
        verify(bookingRepository).findById(100L);
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void createPayment_wrongUserOwnership() {
        // Arrange
        setAuthenticatedUser(5L);

        CreatePaymentRequest request = new CreatePaymentRequest(1L, "UPI");

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(99L);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        // Act
        AccessDeniedException exception = assertThrows(
                AccessDeniedException.class,
                () -> paymentService.createPayment(request)
        );

        // Assert
        assertEquals("You are not allowed to access this booking", exception.getMessage());
        verify(bookingRepository).findById(1L);
        verify(paymentMapper, never()).toEntity(any(), any());
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void confirmPayment_success() {
        // Arrange
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStatus(BookingStatus.PENDING_PAYMENT);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        // Act
        paymentService.confirmPayment(1L);

        // Assert
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
        verify(bookingRepository).findById(1L);
        verify(bookingRepository).save(booking);
    }

    private void setAuthenticatedUser(Long userId) {
        AuthenticatedUserPrincipal principal = new AuthenticatedUserPrincipal(
                userId,
                "user@example.com",
                "password",
                List.of()
        );

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
