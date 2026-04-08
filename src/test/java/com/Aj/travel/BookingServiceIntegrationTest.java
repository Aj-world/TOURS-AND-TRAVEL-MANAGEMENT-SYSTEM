package com.Aj.travel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.Aj.travel.DTO.BookingRequest;
import com.Aj.travel.Entity.Booking;
import com.Aj.travel.Entity.BookingStatus;
import com.Aj.travel.Entity.PaymentStatus;
import com.Aj.travel.Entity.User;
import com.Aj.travel.Entity.UserRole;
import com.Aj.travel.Exception.ResourceNotFoundException;
import com.Aj.travel.Service.BookingService;
import com.Aj.travel.Service.RegistrationService;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BookingServiceIntegrationTest {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private RegistrationService registrationService;

	@Test
	void createPendingBookingCreatesPaymentAndPackageForOwner() {
		User user = registerUser("traveler@example.com");
		BookingRequest request = bookingRequest("Goa", 3, 7500);

		Booking booking = bookingService.createPendingBooking(user.getEmail(), request);

		assertTrue(booking.getBookId() > 0);
		assertEquals(BookingStatus.PENDING_PAYMENT, booking.getStatus());
		assertEquals(7500, booking.getTotalAmount());
		assertEquals("Goa", booking.getPackage1().getPackageName());
		assertEquals("CUSTOM", booking.getPackage1().getPackageType());
		assertEquals(user.getEmail(), booking.getUser().getEmail());
		assertNotNull(booking.getPayment());
		assertEquals(PaymentStatus.CREATED, booking.getPayment().getStatus());
		assertEquals(7500, bookingService.getBookingPrice(booking.getBookId(), user.getEmail()));
	}

	@Test
	void findOwnedBookingRejectsAccessFromAnotherUser() {
		User owner = registerUser("owner@example.com");
		User stranger = registerUser("stranger@example.com");
		Booking booking = bookingService.createPendingBooking(owner.getEmail(), bookingRequest("Darjeeling", 2, 6200));

		assertThrows(ResourceNotFoundException.class,
				() -> bookingService.findOwnedBooking(booking.getBookId(), stranger.getEmail()));
	}

	private User registerUser(String email) {
		User user = new User();
		user.setUserName1("Test User");
		user.setEmail(email);
		user.setUserPassword("Password123!");
		user.setUserPhoneNO("9999999999");
		user.setUserAddresh("Test Address");
		return registrationService.register(user, UserRole.USER);
	}

	private BookingRequest bookingRequest(String location, int guests, int price) {
		BookingRequest request = new BookingRequest();
		request.setName("Traveler");
		request.setEmail("traveler@example.com");
		request.setPhone("9999999999");
		request.setAddress("Test Address");
		request.setLocation(location);
		request.setGuests(guests);
		request.setArivalDate(LocalDate.now().plusDays(5));
		request.setLeavingDate(LocalDate.now().plusDays(8));
		request.setPrice(price);
		return request;
	}
}

