package com.aj.travel.booking.controller;

import com.aj.travel.booking.dto.BookingResponse;
import com.aj.travel.booking.dto.CreateBookingRequest;
import com.aj.travel.booking.service.BookingService;
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
    public BookingResponse createBooking(@Valid @RequestBody CreateBookingRequest request) {

        return bookingService.createBooking(request);
    }

    @GetMapping("/user/{userId}")
    public List<BookingResponse> getUserBookings(@PathVariable Long userId) {

        return bookingService.getUserBookings(userId);
    }

}
