package com.aj.travel.Controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aj.travel.DTO.BookingRequest;
import com.aj.travel.Entity.Booking;
import com.aj.travel.Service.BookingService;
import com.aj.travel.Service.PaymentService;

@Controller
public class BookingController {

	private final BookingService bookingService;
	private final PaymentService paymentService;

	public BookingController(BookingService bookingService, PaymentService paymentService) {
		this.bookingService = bookingService;
		this.paymentService = paymentService;
	}

	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/Packge_process")
	public String createBooking(@ModelAttribute BookingRequest bookingRequest, Model model, Principal principal) {
		Booking booking = bookingService.createPendingBooking(principal.getName(), bookingRequest);
		model.addAttribute("name", booking.getUser().getUserName1());
		model.addAttribute("bookingId", booking.getBookId());
		model.addAttribute("price", booking.getTotalAmount());
		model.addAttribute("razorpayKeyId", paymentService.getRazorpayKeyId());
		return "/User/Pament_Page";
	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/Packge_process2")
	@ResponseBody
	public Map<String, Object> bookingPrice(@RequestParam("bookingId") int bookingId, Principal principal) {
		return Map.of("price", bookingService.getBookingPrice(bookingId, principal.getName()));
	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/Pament_Page_process")
	public String paymentSuccessPage(@RequestParam("bookingId") int bookingId, Principal principal, Model model) {
		Map<String, Object> data = bookingService.paymentPageData(bookingId, principal.getName());
		model.addAttribute("name", data.get("name"));
		return "/Authentication/Sucess_page";
	}
}

