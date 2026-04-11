package com.aj.travel.booking.service;

import com.aj.travel.booking.domain.Booking;
import com.aj.travel.booking.domain.BookingStatus;
import com.aj.travel.booking.dto.BookingResponse;
import com.aj.travel.booking.dto.CreateBookingRequest;
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

    public BookingResponse createBooking(CreateBookingRequest request) {

        TravelPackage travelPackage =
                packageRepository.findById(request.getPackageId())
                        .orElseThrow(() -> new RuntimeException("Package not found"));

        Booking booking = new Booking();

        booking.setUserId(request.getUserId());
        booking.setPackageId(request.getPackageId());
        booking.setGuestCount(request.getGuestCount());
        booking.setTotalPrice(travelPackage.getPrice());
        booking.setStatus(BookingStatus.PENDING_PAYMENT);

        return mapToResponse(bookingRepository.save(booking));
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public BookingResponse mapToResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getUserId(),
                booking.getPackageId(),
                booking.getGuestCount(),
                booking.getTotalPrice(),
                booking.getStatus() != null ? booking.getStatus().name() : null,
                booking.getBookingDate()
        );
    }
}
