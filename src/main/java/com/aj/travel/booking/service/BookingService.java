package com.aj.travel.booking.service;

import com.aj.travel.booking.domain.Booking;
import com.aj.travel.booking.domain.BookingStatus;
import com.aj.travel.booking.repository.BookingRepository;
import com.aj.travel.packages.domain.TravelPackage;
import com.aj.travel.packages.repository.TravelPackageRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TravelPackageRepository packageRepository;

    public Booking createBooking(Long userId, Long packageId) {

        TravelPackage travelPackage =
                packageRepository.findById(packageId)
                        .orElseThrow(() -> new RuntimeException("Package not found"));

        Booking booking = new Booking();

        booking.setUserId(userId);
        booking.setPackageId(packageId);
        booking.setTotalPrice(travelPackage.getPrice());
        booking.setStatus(BookingStatus.PENDING_PAYMENT);

        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}