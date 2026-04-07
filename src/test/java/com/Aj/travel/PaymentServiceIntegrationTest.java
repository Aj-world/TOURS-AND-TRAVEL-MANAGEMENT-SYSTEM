package com.aj.travel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.aj.travel.DTO.BookingRequest;
import com.aj.travel.DTO.PaymentVerifyRequest;
import com.aj.travel.Entity.Booking;
import com.aj.travel.Entity.BookingStatus;
import com.aj.travel.Entity.Payment;
import com.aj.travel.Entity.PaymentStatus;
import com.aj.travel.Entity.User;
import com.aj.travel.Entity.UserRole;
import com.aj.travel.Exception.BadRequestException;
import com.aj.travel.Repository.BookingRepository;
import com.aj.travel.Repository.PaymentRepository;
import com.aj.travel.Service.BookingService;
import com.aj.travel.Service.PaymentService;
import com.aj.travel.Service.RegistrationService;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PaymentServiceIntegrationTest {

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Value("${razorpay.secret}")
	private String razorpaySecret;

	@Test
	void verifyAndConfirmMarksPaymentSuccessfulForOwnedBooking() throws Exception {
		User user = registerUser("payer@example.com");
		Booking booking = bookingService.createPendingBooking(user.getEmail(), bookingRequest());
		Payment payment = booking.getPayment();
		payment.setRazorpayOrderId("order_test_123");
		paymentRepository.save(payment);

		PaymentVerifyRequest request = new PaymentVerifyRequest();
		request.setBookingId(booking.getBookId());
		request.setRazorpayOrderId(payment.getRazorpayOrderId());
		request.setRazorpayPaymentId("pay_test_456");
		request.setRazorpaySignature(signatureFor(payment.getRazorpayOrderId(), "pay_test_456"));

		paymentService.verifyAndConfirm(request, user.getEmail());

		Booking savedBooking = bookingRepository.findById(booking.getBookId()).orElseThrow();
		assertEquals(BookingStatus.CONFIRMED, savedBooking.getStatus());
		assertEquals(PaymentStatus.SUCCESS, savedBooking.getPayment().getStatus());
		assertEquals("pay_test_456", savedBooking.getPayment().getRazorpayPaymentId());
	}

	@Test
	void verifyAndConfirmRejectsMismatchedOrderId() {
		User user = registerUser("mismatch@example.com");
		Booking booking = bookingService.createPendingBooking(user.getEmail(), bookingRequest());
		Payment payment = booking.getPayment();
		payment.setRazorpayOrderId("order_expected");
		paymentRepository.save(payment);

		PaymentVerifyRequest request = new PaymentVerifyRequest();
		request.setBookingId(booking.getBookId());
		request.setRazorpayOrderId("order_other");
		request.setRazorpayPaymentId("pay_test_456");
		request.setRazorpaySignature("invalid");

		assertThrows(BadRequestException.class, () -> paymentService.verifyAndConfirm(request, user.getEmail()));
	}

	private User registerUser(String email) {
		User user = new User();
		user.setUserName1("Payment User");
		user.setEmail(email);
		user.setUserPassword("Password123!");
		user.setUserPhoneNO("9999999999");
		user.setUserAddresh("Test Address");
		return registrationService.register(user, UserRole.USER);
	}

	private BookingRequest bookingRequest() {
		BookingRequest request = new BookingRequest();
		request.setName("Traveler");
		request.setEmail("payer@example.com");
		request.setPhone("9999999999");
		request.setAddress("Test Address");
		request.setLocation("Kerala");
		request.setGuests(2);
		request.setArivalDate(LocalDate.now().plusDays(10));
		request.setLeavingDate(LocalDate.now().plusDays(14));
		request.setPrice(8900);
		return request;
	}

	private String signatureFor(String orderId, String paymentId) throws Exception {
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(new SecretKeySpec(razorpaySecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
		byte[] digest = mac.doFinal((orderId + "|" + paymentId).getBytes(StandardCharsets.UTF_8));
		StringBuilder hex = new StringBuilder(digest.length * 2);
		for (byte b : digest) {
			hex.append(String.format("%02x", b));
		}
		return hex.toString();
	}
}

