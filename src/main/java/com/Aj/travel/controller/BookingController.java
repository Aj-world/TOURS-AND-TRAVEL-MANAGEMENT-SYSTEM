package com.aj.travel.controller;

import static com.aj.travel.constants.ApiPaths.BOOKINGS;
import static com.aj.travel.constants.ApiPaths.BOOKING_PRICE;
import static com.aj.travel.constants.SecurityConstants.HAS_ROLE_USER;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aj.travel.dto.ApiResponse;
import com.aj.travel.dto.BookingRequest;
import com.aj.travel.entity.Booking;
import com.aj.travel.service.BookingService;
import com.aj.travel.service.PaymentService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(BOOKINGS)
@PreAuthorize(HAS_ROLE_USER)
@Slf4j
public class BookingController {

	private final BookingService bookingService;
	private final PaymentService paymentService;

	public BookingController(BookingService bookingService, PaymentService paymentService) {
		this.bookingService = bookingService;
		this.paymentService = paymentService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Map<String, Object>>> createBooking(
			@Valid @RequestBody BookingRequest bookingRequest,
			Principal principal) {
		log.info("API request: create booking | user={} | location={} | guests={}",
				principal.getName(), bookingRequest.getLocation(), bookingRequest.getGuests());
		Booking booking = bookingService.createPendingBooking(principal.getName(), bookingRequest);
		log.info("API response: booking created | user={} | bookingId={}",
				principal.getName(), booking.getBookId());
		return ResponseEntity.ok(ApiResponse.success(
				"Booking created",
				Map.of(
						"name", booking.getUser().getUserName(),
						"bookingId", booking.getBookId(),
						"price", booking.getTotalAmount(),
						"status", booking.getStatus().name(),
						"razorpayKeyId", paymentService.getRazorpayKeyId())));
	}

	@GetMapping(BOOKING_PRICE)
	public ResponseEntity<ApiResponse<Map<String, Object>>> getBookingPrice(@PathVariable int bookingId, Principal principal) {
		log.info("API request: fetch booking price | user={} | bookingId={}", principal.getName(), bookingId);
		return ResponseEntity.ok(ApiResponse.success(
				"Booking price fetched",
				Map.of("price", bookingService.getBookingPrice(bookingId, principal.getName()))));
	}
}
