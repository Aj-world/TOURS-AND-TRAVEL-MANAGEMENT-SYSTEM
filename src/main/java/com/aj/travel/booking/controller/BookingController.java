package com.aj.travel.booking.controller;

import com.aj.travel.booking.domain.Booking;
import com.aj.travel.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public Booking createBooking(
            @RequestParam Long userId,
            @RequestParam Long packageId
    ) {

        return bookingService.createBooking(userId, packageId);
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getUserBookings(@PathVariable Long userId) {

        return bookingService.getUserBookings(userId);
    }

}