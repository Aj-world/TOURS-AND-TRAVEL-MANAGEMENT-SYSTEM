package com.aj.travel.booking.service;

import com.aj.travel.booking.domain.Booking;
import com.aj.travel.booking.domain.BookingStatus;
import com.aj.travel.booking.dto.BookingResponse;
import com.aj.travel.booking.dto.CreateBookingRequest;
import com.aj.travel.booking.mapper.BookingMapper;
import com.aj.travel.booking.repository.BookingRepository;
import com.aj.travel.common.exception.ResourceNotFoundException;
import com.aj.travel.packages.domain.TravelPackage;
import com.aj.travel.packages.repository.TravelPackageRepository;
import com.aj.travel.user.domain.User;
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
    private final BookingMapper bookingMapper;

    public BookingResponse createBooking(CreateBookingRequest request) {

        TravelPackage travelPackage =
                packageRepository.findById(request.getPackageId())
                        .orElseThrow(() -> new ResourceNotFoundException("Package not found"));

        User user = new User();
        user.setId(request.getUserId());

        Booking booking = bookingMapper.toEntity(request, user, travelPackage);
        booking.setStatus(BookingStatus.PENDING_PAYMENT);

        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(bookingMapper::toResponse)
                .toList();
    }
}
