package com.Aj.travel.controller;

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

import com.Aj.travel.DTO.BookingRequest;
import com.Aj.travel.Entity.Booking;
import com.Aj.travel.Service.BookingService;
import com.Aj.travel.Service.PaymentService;

@Controller
@RequestMapping("/bookings")
@PreAuthorize("hasRole('USER')")
public class BookingController {

	private static final Logger log = LoggerFactory.getLogger(BookingController.class);

	private final BookingService bookingService;
	private final PaymentService paymentService;

	public BookingController(BookingService bookingService, PaymentService paymentService) {
		this.bookingService = bookingService;
		this.paymentService = paymentService;
	}

	@PostMapping
	public String createBooking(@ModelAttribute BookingRequest bookingRequest, Model model, Principal principal) {
		Booking booking = bookingService.createPendingBooking(principal.getName(), bookingRequest);
		log.info("Created pending booking with id={} for user={}", booking.getBookId(), principal.getName());
		model.addAttribute("name", booking.getUser().getUserName1());
		model.addAttribute("bookingId", booking.getBookId());
		model.addAttribute("price", booking.getTotalAmount());
		model.addAttribute("razorpayKeyId", paymentService.getRazorpayKeyId());
		return "/User/Pament_Page";
	}

	@GetMapping("/{bookingId}/price")
	public ResponseEntity<Map<String, Object>> getBookingPrice(@PathVariable int bookingId, Principal principal) {
		return ResponseEntity.ok(Map.of("price", bookingService.getBookingPrice(bookingId, principal.getName())));
	}
}
