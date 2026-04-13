package com.aj.travel.booking.service;

import com.aj.travel.booking.domain.Booking;
import com.aj.travel.booking.domain.BookingStatus;
import com.aj.travel.booking.dto.BookingResponse;
import com.aj.travel.booking.dto.CreateBookingRequest;
import com.aj.travel.booking.mapper.BookingMapper;
import com.aj.travel.booking.repository.BookingRepository;
import com.aj.travel.common.exception.ResourceNotFoundException;
import com.aj.travel.common.security.AuthenticatedUserPrincipal;
import com.aj.travel.packages.domain.TravelPackage;
import com.aj.travel.packages.repository.TravelPackageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private TravelPackageRepository packageRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingService bookingService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void createBooking_success() {
        // Arrange
        setAuthenticatedUser(7L);

        TravelPackage travelPackage = new TravelPackage();
        travelPackage.setId(1L);
        travelPackage.setPrice(BigDecimal.valueOf(1000));

        CreateBookingRequest request = new CreateBookingRequest(1L, 2);

        Booking booking = new Booking();
        booking.setUserId(7L);
        booking.setPackageId(1L);
        booking.setGuestCount(2);
        booking.setTotalPrice(BigDecimal.valueOf(1000));

        Booking savedBooking = new Booking();
        savedBooking.setId(10L);
        savedBooking.setUserId(7L);
        savedBooking.setPackageId(1L);
        savedBooking.setGuestCount(2);
        savedBooking.setTotalPrice(BigDecimal.valueOf(1000));
        savedBooking.setStatus(BookingStatus.PENDING_PAYMENT);
        savedBooking.setBookingDate(LocalDateTime.now());

        BookingResponse expectedResponse = new BookingResponse(
                10L,
                7L,
                1L,
                2,
                BigDecimal.valueOf(1000),
                "PENDING_PAYMENT",
                savedBooking.getBookingDate()
        );

        when(packageRepository.findById(1L)).thenReturn(Optional.of(travelPackage));
        when(bookingMapper.toEntity(request, 7L, travelPackage)).thenReturn(booking);
        when(bookingRepository.save(booking)).thenReturn(savedBooking);
        when(bookingMapper.toResponse(savedBooking)).thenReturn(expectedResponse);

        // Act
        BookingResponse response = bookingService.createBooking(request);

        // Assert
        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals("PENDING_PAYMENT", response.getStatus());
        assertEquals(BookingStatus.PENDING_PAYMENT, booking.getStatus());
        verify(packageRepository).findById(1L);
        verify(bookingMapper).toEntity(request, 7L, travelPackage);
        verify(bookingRepository).save(booking);
        verify(bookingMapper).toResponse(savedBooking);
    }

    @Test
    void createBooking_packageNotFound() {
        // Arrange
        setAuthenticatedUser(7L);

        CreateBookingRequest request = new CreateBookingRequest(99L, 2);
        when(packageRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> bookingService.createBooking(request)
        );

        // Assert
        assertEquals("Package not found", exception.getMessage());
        verify(packageRepository).findById(99L);
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void createBooking_invalidGuestCount() {
        // Arrange
        setAuthenticatedUser(7L);

        TravelPackage travelPackage = new TravelPackage();
        travelPackage.setId(1L);
        travelPackage.setPrice(BigDecimal.valueOf(1000));

        CreateBookingRequest request = new CreateBookingRequest(1L, 0);

        when(packageRepository.findById(1L)).thenReturn(Optional.of(travelPackage));
        when(bookingMapper.toEntity(request, 7L, travelPackage))
                .thenThrow(new IllegalArgumentException("Guest count must be at least 1"));

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bookingService.createBooking(request)
        );

        // Assert
        assertEquals("Guest count must be at least 1", exception.getMessage());
        verify(packageRepository).findById(1L);
        verify(bookingMapper).toEntity(request, 7L, travelPackage);
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void getMyBookings_success() {
        // Arrange
        setAuthenticatedUser(7L);

        Booking booking1 = new Booking();
        booking1.setId(1L);
        Booking booking2 = new Booking();
        booking2.setId(2L);

        BookingResponse response1 = new BookingResponse();
        response1.setId(1L);
        BookingResponse response2 = new BookingResponse();
        response2.setId(2L);

        when(bookingRepository.findByUserId(7L)).thenReturn(List.of(booking1, booking2));
        when(bookingMapper.toResponse(booking1)).thenReturn(response1);
        when(bookingMapper.toResponse(booking2)).thenReturn(response2);

        // Act
        List<BookingResponse> responses = bookingService.getMyBookings();

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals(1L, responses.get(0).getId());
        assertEquals(2L, responses.get(1).getId());
        verify(bookingRepository).findByUserId(7L);
        verify(bookingMapper).toResponse(booking1);
        verify(bookingMapper).toResponse(booking2);
    }

    @Test
    void getAllBookings_adminAccess() {
        // Arrange
        Booking booking1 = new Booking();
        booking1.setId(11L);
        Booking booking2 = new Booking();
        booking2.setId(12L);

        BookingResponse response1 = new BookingResponse();
        response1.setId(11L);
        BookingResponse response2 = new BookingResponse();
        response2.setId(12L);

        when(bookingRepository.findAll()).thenReturn(List.of(booking1, booking2));
        when(bookingMapper.toResponse(booking1)).thenReturn(response1);
        when(bookingMapper.toResponse(booking2)).thenReturn(response2);

        // Act
        List<BookingResponse> responses = bookingService.getAllBookings();

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals(11L, responses.get(0).getId());
        assertEquals(12L, responses.get(1).getId());
        verify(bookingRepository).findAll();
        verify(bookingMapper).toResponse(booking1);
        verify(bookingMapper).toResponse(booking2);
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
