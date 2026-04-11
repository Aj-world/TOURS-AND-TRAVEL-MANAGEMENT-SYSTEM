package com.aj.travel.payment.controller;

import com.aj.travel.payment.dto.CreatePaymentRequest;
import com.aj.travel.payment.dto.PaymentResponse;
import com.aj.travel.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponse createPayment(@Valid @RequestBody CreatePaymentRequest request) {

        return paymentService.createPayment(request);
    }

    @PostMapping("/confirm/{bookingId}")
    public void confirmPayment(@PathVariable Long bookingId) {

        paymentService.confirmPayment(bookingId);
    }

}
