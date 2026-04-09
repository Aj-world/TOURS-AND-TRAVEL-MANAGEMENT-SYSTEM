package com.aj.travel.controller;

import static com.aj.travel.constants.ApiPaths.PAYMENTS;
import static com.aj.travel.constants.ApiPaths.PAYMENT_ORDERS;
import static com.aj.travel.constants.ApiPaths.PAYMENT_SUCCESS;
import static com.aj.travel.constants.ApiPaths.PAYMENT_VERIFY;
import static com.aj.travel.constants.SecurityConstants.HAS_ROLE_USER;

import java.security.Principal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aj.travel.dto.ApiResponse;
import com.aj.travel.dto.CreateOrderRequest;
import com.aj.travel.dto.PaymentVerifyRequest;
import com.aj.travel.service.BookingService;
import com.aj.travel.service.PaymentService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(PAYMENTS)
@PreAuthorize(HAS_ROLE_USER)
public class PaymentController {

	private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

	private final PaymentService paymentService;
	private final BookingService bookingService;

	public PaymentController(PaymentService paymentService, BookingService bookingService) {
		this.paymentService = paymentService;
		this.bookingService = bookingService;
	}

	@ResponseBody
	@PostMapping(PAYMENT_ORDERS)
	public ResponseEntity<ApiResponse<String>> createOrder(@Valid @RequestBody CreateOrderRequest request, Principal principal)
			throws Exception {
		log.info("Creating payment order for bookingId={} and user={}", request.getBookingId(), principal.getName());
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(ApiResponse.success(
						"Payment order created",
						paymentService.createOrder(request.getBookingId(), principal.getName())));
	}

	@ResponseBody
	@PostMapping(PAYMENT_VERIFY)
	public ResponseEntity<ApiResponse<Map<String, Object>>> verifyPayment(
			@Valid @RequestBody PaymentVerifyRequest request,
			Principal principal) throws Exception {
		paymentService.verifyAndConfirm(request, principal.getName());
		log.info("Verified payment for bookingId={} and user={}", request.getBookingId(), principal.getName());
		return ResponseEntity.ok(ApiResponse.success(
				"Payment verified",
				Map.of("status", "verified", "bookingId", request.getBookingId())));
	}

	@GetMapping(PAYMENT_SUCCESS)
	public String showPaymentSuccessPage(@RequestParam("bookingId") int bookingId, Principal principal, Model model) {
		Map<String, Object> data = bookingService.paymentPageData(bookingId, principal.getName());
		model.addAttribute("name", data.get("name"));
		return "/Authentication/Sucess_page";
	}
}
