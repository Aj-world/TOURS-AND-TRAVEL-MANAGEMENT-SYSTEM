package com.aj.travel.controller;

import static com.aj.travel.constants.ApiPaths.BOOKINGS;
import static com.aj.travel.constants.ApiPaths.BOOKING_PRICE;
import static com.aj.travel.constants.SecurityConstants.HAS_ROLE_USER;

import java.security.Principal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aj.travel.dto.BookingRequest;
import com.aj.travel.entity.Booking;
import com.aj.travel.service.BookingService;
import com.aj.travel.service.PaymentService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(BOOKINGS)
@PreAuthorize(HAS_ROLE_USER)
public class BookingController {

	private static final Logger log = LoggerFactory.getLogger(BookingController.class);

	private final BookingService bookingService;
	private final PaymentService paymentService;

	public BookingController(BookingService bookingService, PaymentService paymentService) {
		this.bookingService = bookingService;
		this.paymentService = paymentService;
	}

	@PostMapping
	public String createBooking(@Valid @ModelAttribute BookingRequest bookingRequest, Model model, Principal principal) {
		Booking booking = bookingService.createPendingBooking(principal.getName(), bookingRequest);
		log.info("Created pending booking with id={} for user={}", booking.getBookId(), principal.getName());
		model.addAttribute("name", booking.getUser().getUserName());
		model.addAttribute("bookingId", booking.getBookId());
		model.addAttribute("price", booking.getTotalAmount());
		model.addAttribute("razorpayKeyId", paymentService.getRazorpayKeyId());
		return "/User/Pament_Page";
	}

	@GetMapping(BOOKING_PRICE)
	public ResponseEntity<Map<String, Object>> getBookingPrice(@PathVariable int bookingId, Principal principal) {
		return ResponseEntity.ok(Map.of("price", bookingService.getBookingPrice(bookingId, principal.getName())));
	}
}
