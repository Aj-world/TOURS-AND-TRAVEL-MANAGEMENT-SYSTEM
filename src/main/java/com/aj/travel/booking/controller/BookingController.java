package com.aj.travel.booking.controller;

import com.aj.travel.booking.dto.BookingResponse;
import com.aj.travel.booking.dto.CreateBookingRequest;
import com.aj.travel.booking.service.BookingService;
import com.aj.travel.common.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<BookingResponse> createBooking(@Valid @RequestBody CreateBookingRequest request) {

        return new ApiResponse<>(
                true,
                "Booking created successfully",
                bookingService.createBooking(request)
        );
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<List<BookingResponse>> getMyBookings() {

        return new ApiResponse<>(
                true,
                "Bookings retrieved successfully",
                bookingService.getMyBookings()
        );
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<BookingResponse>> getAllBookings() {

        return new ApiResponse<>(
                true,
                "All bookings retrieved successfully",
                bookingService.getAllBookings()
        );
    }

}
