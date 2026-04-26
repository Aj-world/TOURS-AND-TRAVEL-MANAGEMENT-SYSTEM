package com.aj.travel.payment.controller;

import com.aj.travel.common.api.ApiResponse;
import com.aj.travel.payment.dto.CreatePaymentRequest;
import com.aj.travel.payment.dto.PaymentResponse;
import com.aj.travel.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<PaymentResponse> createPayment(@Valid @RequestBody CreatePaymentRequest request) {

        return new ApiResponse<>(
                true,
                "Payment created successfully",
                paymentService.createPayment(request)
        );
    }

    @PostMapping("/confirm/{bookingId}")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<Void> confirmPayment(@PathVariable Long bookingId) {

        paymentService.confirmPayment(bookingId);

        return new ApiResponse<>(
                true,
                "Payment confirmed successfully",
                null
        );
    }
}
