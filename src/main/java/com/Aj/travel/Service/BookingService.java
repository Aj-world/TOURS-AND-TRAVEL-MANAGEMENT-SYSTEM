package com.aj.travel.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.travel.dto.BookingRequest;
import com.aj.travel.entity.Booking;
import com.aj.travel.entity.BookingStatus;
import com.aj.travel.entity.Package;
import com.aj.travel.entity.Payment;
import com.aj.travel.entity.User;
import com.aj.travel.exception.BadRequestException;
import com.aj.travel.exception.ResourceNotFoundException;
import com.aj.travel.repository.BookingRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingService {

	private final BookingRepository bookingRepository;
	private final RegistrationService registrationService;

	public BookingService(BookingRepository bookingRepository, RegistrationService registrationService) {
		this.bookingRepository = bookingRepository;
		this.registrationService = registrationService;
	}

	@Transactional
	public Booking createPendingBooking(String email, BookingRequest request) {
		log.info("Creating booking | user={} | location={} | guests={} | amount={}",
				email, request.getLocation(), request.getGuests(), request.getPrice());
		if (request.getLeavingDate().isBefore(request.getArivalDate())) {
			log.warn("Booking creation rejected | user={} | arrivalDate={} | leavingDate={} | reason=invalid-date-range",
					email, request.getArivalDate(), request.getLeavingDate());
			throw new BadRequestException("Leaving date cannot be before arrival date");
		}

		User user = registrationService.findByEmail(email);

		Package travelPackage = new Package();
		travelPackage.setPackageName(request.getLocation());
		travelPackage.setPackageType("CUSTOM");
		travelPackage.setPrice(request.getPrice());
		travelPackage.setUser(user);

		Payment payment = new Payment();
		payment.setAmount(request.getPrice());
		payment.setPaymentType("RAZORPAY");

		Booking booking = new Booking();
		booking.setGuest(request.getGuests());
		booking.setArrivalDate(request.getArivalDate());
		booking.setLeavingDate(request.getLeavingDate());
		booking.setTotalAmount(request.getPrice());
		booking.setStatus(BookingStatus.PENDING_PAYMENT);
		booking.setUser(user);
		booking.setTravelPackage(travelPackage);
		booking.setPayment(payment);
		payment.setBooking(booking);

		Booking savedBooking = bookingRepository.save(booking);
		log.info("Booking confirmed | bookingId={} | user={} | location={} | guests={} | amount={}",
				savedBooking.getBookId(), email, request.getLocation(), request.getGuests(), request.getPrice());
		return savedBooking;
	}

	@Transactional(readOnly = true)
	public int getBookingPrice(int bookingId, String email) {
		log.debug("Fetching booking price | bookingId={} | user={}", bookingId, email);
		return findOwnedBooking(bookingId, email).getTotalAmount();
	}

	@Transactional(readOnly = true)
	public Booking findOwnedBooking(int bookingId, String email) {
		log.debug("Looking up booking | bookingId={} | user={}", bookingId, email);
		return bookingRepository.findOwnedBooking(bookingId, email)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found: " + bookingId));
	}

	@Transactional(readOnly = true)
	public Map<String, Object> paymentPageData(int bookingId, String email) {
		log.debug("Preparing payment page data | bookingId={} | user={}", bookingId, email);
		Booking booking = findOwnedBooking(bookingId, email);
		return Map.of(
				"bookingId", booking.getBookId(),
				"price", booking.getTotalAmount(),
				"name", booking.getUser().getUserName());
	}
}

