package com.aj.travel.payment.controller;

import com.aj.travel.payment.domain.Payment;
import com.aj.travel.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{bookingId}")
    public Payment createPayment(@PathVariable Long bookingId) {

        return paymentService.createPayment(bookingId);
    }

    @PostMapping("/confirm/{bookingId}")
    public void confirmPayment(@PathVariable Long bookingId) {

        paymentService.confirmPayment(bookingId);
    }

}