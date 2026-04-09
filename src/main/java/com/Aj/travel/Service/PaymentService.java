package com.aj.travel.service;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.travel.dto.PaymentVerifyRequest;
import com.aj.travel.entity.Booking;
import com.aj.travel.entity.BookingStatus;
import com.aj.travel.entity.Payment;
import com.aj.travel.entity.PaymentStatus;
import com.aj.travel.exception.BadRequestException;
import com.aj.travel.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentService {

	private final BookingService bookingService;
	private final PaymentRepository paymentRepository;
	private final String razorpayKeyId;
	private final String razorpaySecret;

	public PaymentService(BookingService bookingService,
			PaymentRepository paymentRepository,
			@Value("${razorpay.key}") String razorpayKeyId,
			@Value("${razorpay.secret}") String razorpaySecret) {
		this.bookingService = bookingService;
		this.paymentRepository = paymentRepository;
		this.razorpayKeyId = razorpayKeyId;
		this.razorpaySecret = razorpaySecret;
	}

	@Transactional
	public String createOrder(int bookingId, String email) throws Exception {
		log.info("Payment order creation started | bookingId={} | user={}", bookingId, email);
		Booking booking = bookingService.findOwnedBooking(bookingId, email);
		if (booking.getStatus() != BookingStatus.PENDING_PAYMENT) {
			log.warn("Payment order creation rejected | bookingId={} | user={} | status={} | reason=invalid-booking-status",
					bookingId, email, booking.getStatus());
			throw new BadRequestException("Booking is not eligible for payment");
		}

		Payment payment = booking.getPayment();
		if (payment == null) {
			log.warn("Payment order creation rejected | bookingId={} | user={} | reason=missing-payment-record",
					bookingId, email);
			throw new BadRequestException("Payment record missing for booking");
		}

		RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpaySecret);
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", booking.getTotalAmount() * 100);
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "booking-" + booking.getBookId());

		Order order = razorpay.orders.create(orderRequest);

		payment.setRazorpayOrderId(order.get("id"));
		payment.setStatus(PaymentStatus.CREATED);
		paymentRepository.save(payment);
		log.info("Payment order created | bookingId={} | user={} | orderId={}",
				bookingId, email, payment.getRazorpayOrderId());

		return order.toString();
	}

	@Transactional
	public void verifyAndConfirm(PaymentVerifyRequest request, String email) throws Exception {
		log.info("Payment verification started | bookingId={} | user={} | orderId={}",
				request.getBookingId(), email, request.getRazorpayOrderId());
		Booking booking = bookingService.findOwnedBooking(request.getBookingId(), email);
		Payment payment = booking.getPayment();
		if (payment == null) {
			log.warn("Payment verification rejected | bookingId={} | user={} | reason=missing-payment-record",
					request.getBookingId(), email);
			throw new BadRequestException("Payment record missing for booking");
		}
		if (!request.getRazorpayOrderId().equals(payment.getRazorpayOrderId())) {
			log.warn("Payment verification rejected | bookingId={} | user={} | reason=order-id-mismatch",
					request.getBookingId(), email);
			throw new BadRequestException("Order id mismatch");
		}

		JSONObject verifyJson = new JSONObject(Map.of(
				"razorpay_order_id", request.getRazorpayOrderId(),
				"razorpay_payment_id", request.getRazorpayPaymentId(),
				"razorpay_signature", request.getRazorpaySignature()));

		boolean valid = Utils.verifyPaymentSignature(verifyJson, razorpaySecret);
		if (!valid) {
			payment.setStatus(PaymentStatus.FAILED);
			paymentRepository.save(payment);
			log.warn("Payment verification failed | bookingId={} | user={} | orderId={} | reason=invalid-signature",
					request.getBookingId(), email, request.getRazorpayOrderId());
			throw new BadRequestException("Payment signature verification failed");
		}

		payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
		payment.setRazorpaySignature(request.getRazorpaySignature());
		payment.setStatus(PaymentStatus.SUCCESS);
		booking.setStatus(BookingStatus.CONFIRMED);

		paymentRepository.save(payment);
		log.info("Booking confirmed | bookingId={} | user={} | paymentId={}",
				request.getBookingId(), email, request.getRazorpayPaymentId());
	}

	public String getRazorpayKeyId() {
		return razorpayKeyId;
	}
}
