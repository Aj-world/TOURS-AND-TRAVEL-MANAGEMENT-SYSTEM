package com.aj.travel.Controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aj.travel.DTO.CreateOrderRequest;
import com.aj.travel.DTO.PaymentVerifyRequest;
import com.aj.travel.Service.PaymentService;

import jakarta.validation.Valid;

@RestController
public class PaymentController {

	private final PaymentService paymentService;

	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/createOrder")
	public String createOrder(@Valid @RequestBody CreateOrderRequest request, Principal principal) throws Exception {
		return paymentService.createOrder(request.getBookingId(), principal.getName());
	}

	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/payment/verify")
	public Map<String, Object> verifyPayment(@Valid @RequestBody PaymentVerifyRequest request, Principal principal)
			throws Exception {
		paymentService.verifyAndConfirm(request, principal.getName());
		return Map.of("status", "verified", "bookingId", request.getBookingId());
	}
}

