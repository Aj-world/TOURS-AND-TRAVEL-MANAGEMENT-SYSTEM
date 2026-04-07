package com.aj.travel.Service;

import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.travel.DTO.PaymentVerifyRequest;
import com.aj.travel.Entity.Booking;
import com.aj.travel.Entity.BookingStatus;
import com.aj.travel.Entity.Payment;
import com.aj.travel.Entity.PaymentStatus;
import com.aj.travel.Exception.BadRequestException;
import com.aj.travel.Repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

@Service
public class PaymentService {

	private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

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
		Booking booking = bookingService.findOwnedBooking(bookingId, email);
		if (booking.getStatus() != BookingStatus.PENDING_PAYMENT) {
			log.warn("Rejected order creation for booking id={} email={} because status={}",
					bookingId, email, booking.getStatus());
			throw new BadRequestException("Booking is not eligible for payment");
		}

		Payment payment = booking.getPayment();
		if (payment == null) {
			log.warn("Rejected order creation for booking id={} email={} because payment record is missing",
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
		log.info("Created Razorpay order for booking id={} email={} orderId={}",
				bookingId, email, payment.getRazorpayOrderId());

		return order.toString();
	}

	@Transactional
	public void verifyAndConfirm(PaymentVerifyRequest request, String email) throws Exception {
		Booking booking = bookingService.findOwnedBooking(request.getBookingId(), email);
		Payment payment = booking.getPayment();
		if (payment == null) {
			log.warn("Rejected payment verification for booking id={} email={} because payment record is missing",
					request.getBookingId(), email);
			throw new BadRequestException("Payment record missing for booking");
		}
		if (!request.getRazorpayOrderId().equals(payment.getRazorpayOrderId())) {
			log.warn("Rejected payment verification for booking id={} email={} because order id mismatched",
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
			log.warn("Payment verification failed for booking id={} email={} orderId={}",
					request.getBookingId(), email, request.getRazorpayOrderId());
			throw new BadRequestException("Payment signature verification failed");
		}

		payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
		payment.setRazorpaySignature(request.getRazorpaySignature());
		payment.setStatus(PaymentStatus.SUCCESS);
		booking.setStatus(BookingStatus.CONFIRMED);

		paymentRepository.save(payment);
		log.info("Payment verified for booking id={} email={} paymentId={}",
				request.getBookingId(), email, request.getRazorpayPaymentId());
	}

	public String getRazorpayKeyId() {
		return razorpayKeyId;
	}
}

