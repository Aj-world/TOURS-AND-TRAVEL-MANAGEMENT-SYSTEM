package com.aj.travel.booking.controller;

import com.aj.travel.booking.dto.BookingResponse;
import com.aj.travel.booking.dto.CreateBookingRequest;
import com.aj.travel.booking.service.BookingService;
import com.aj.travel.common.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ApiResponse<BookingResponse> createBooking(@Valid @RequestBody CreateBookingRequest request) {

        return new ApiResponse<>(
                true,
                "Booking created successfully",
                bookingService.createBooking(request)
        );
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<BookingResponse>> getUserBookings(@PathVariable Long userId) {

        return new ApiResponse<>(
                true,
                "Bookings retrieved successfully",
                bookingService.getUserBookings(userId)
        );
    }

}
