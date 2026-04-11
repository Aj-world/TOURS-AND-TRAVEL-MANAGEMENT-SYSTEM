package com.aj.travel.payment.service;

import com.aj.travel.booking.domain.Booking;
import com.aj.travel.booking.domain.BookingStatus;
import com.aj.travel.booking.repository.BookingRepository;
import com.aj.travel.common.exception.ResourceNotFoundException;
import com.aj.travel.payment.domain.Payment;
import com.aj.travel.payment.domain.PaymentStatus;
import com.aj.travel.payment.dto.CreatePaymentRequest;
import com.aj.travel.payment.dto.PaymentResponse;
import com.aj.travel.payment.mapper.PaymentMapper;
import com.aj.travel.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentMapper paymentMapper;

    public PaymentResponse createPayment(CreatePaymentRequest request) {

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        Payment payment = paymentMapper.toEntity(request, booking);
        payment.setStatus(PaymentStatus.CREATED);

        return paymentMapper.toResponse(paymentRepository.save(payment));
    }

    public void confirmPayment(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        booking.setStatus(BookingStatus.CONFIRMED);

        bookingRepository.save(booking);
    }
}
