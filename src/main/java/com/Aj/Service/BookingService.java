package com.Aj.Service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Aj.DTO.BookingRequest;
import com.Aj.Entity.Booking;
import com.Aj.Entity.BookingStatus;
import com.Aj.Entity.Package;
import com.Aj.Entity.Payment;
import com.Aj.Entity.User;
import com.Aj.Exception.BadRequestException;
import com.Aj.Exception.ResourceNotFoundException;
import com.Aj.Repository.BookingRepository;

@Service
public class BookingService {

	private final BookingRepository bookingRepository;
	private final RegistrationService registrationService;

	public BookingService(BookingRepository bookingRepository, RegistrationService registrationService) {
		this.bookingRepository = bookingRepository;
		this.registrationService = registrationService;
	}

	@Transactional
	public Booking createPendingBooking(String email, BookingRequest request) {
		if (request.getLeavingDate().isBefore(request.getArivalDate())) {
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
		booking.setArivalDate(request.getArivalDate());
		booking.setLeavingDate(request.getLeavingDate());
		booking.setTotalAmount(request.getPrice());
		booking.setStatus(BookingStatus.PENDING_PAYMENT);
		booking.setUser(user);
		booking.setPackage1(travelPackage);
		booking.setPayment(payment);
		payment.setBooking(booking);

		return bookingRepository.save(booking);
	}

	@Transactional(readOnly = true)
	public int getBookingPrice(int bookingId, String email) {
		return findOwnedBooking(bookingId, email).getTotalAmount();
	}

	@Transactional(readOnly = true)
	public Booking findOwnedBooking(int bookingId, String email) {
		return bookingRepository.findOwnedBooking(bookingId, email)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found: " + bookingId));
	}

	@Transactional(readOnly = true)
	public Map<String, Object> paymentPageData(int bookingId, String email) {
		Booking booking = findOwnedBooking(bookingId, email);
		return Map.of(
				"bookingId", booking.getBookId(),
				"price", booking.getTotalAmount(),
				"name", booking.getUser().getUserName1());
	}
}
