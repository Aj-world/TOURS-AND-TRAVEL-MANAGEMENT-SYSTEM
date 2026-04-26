package com.aj.travel.booking.service;

import com.aj.travel.auth.security.AuthenticatedUserPrincipal;
import com.aj.travel.booking.domain.Booking;
import com.aj.travel.booking.domain.BookingStatus;
import com.aj.travel.booking.dto.BookingResponse;
import com.aj.travel.booking.dto.CreateBookingRequest;
import com.aj.travel.booking.mapper.BookingMapper;
import com.aj.travel.booking.repository.BookingRepository;
import com.aj.travel.common.exception.ResourceNotFoundException;
import com.aj.travel.packages.domain.TravelPackage;
import com.aj.travel.packages.repository.TravelPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PreAuthorize("hasRole('USER')")
    public BookingResponse createBooking(CreateBookingRequest request) {

        Long currentUserId = getCurrentUserId();

        TravelPackage travelPackage =
                packageRepository.findById(request.getPackageId())
                        .orElseThrow(() -> new ResourceNotFoundException("Package not found"));

        Booking booking = bookingMapper.toEntity(request, currentUserId, travelPackage);
        booking.setStatus(BookingStatus.PENDING_PAYMENT);

        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional(readOnly = true)
    public List<BookingResponse> getMyBookings() {

        Long currentUserId = getCurrentUserId();

        return bookingRepository.findByUserId(currentUserId)
                .stream()
                .map(bookingMapper::toResponse)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(bookingMapper::toResponse)
                .toList();
    }

    // 🔐 Centralized method (clean replacement for SecurityUtils)
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        AuthenticatedUserPrincipal principal =
                (AuthenticatedUserPrincipal) authentication.getPrincipal();

        return principal.getId();
    }
}
