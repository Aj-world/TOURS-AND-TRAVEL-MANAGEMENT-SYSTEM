package com.aj.travel.payment.mapper;

import com.aj.travel.booking.domain.Booking;
import com.aj.travel.payment.domain.Payment;
import com.aj.travel.payment.dto.CreatePaymentRequest;
import com.aj.travel.payment.dto.PaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toEntity(CreatePaymentRequest request, Booking booking) {
        Payment payment = new Payment();
        payment.setBookingId(booking.getId());
        payment.setAmount(booking.getTotalPrice());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setRazorpayOrderId(null);
        payment.setRazorpayPaymentId(null);
        payment.setPaidAt(null);
        return payment;
    }

    public PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getBookingId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getRazorpayOrderId(),
                payment.getRazorpayPaymentId(),
                payment.getStatus() != null ? payment.getStatus().name() : null,
                payment.getPaidAt()
        );
    }
}
