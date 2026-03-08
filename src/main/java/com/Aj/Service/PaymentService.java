package com.Aj.Service;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Aj.DTO.PaymentVerifyRequest;
import com.Aj.Entity.Booking;
import com.Aj.Entity.BookingStatus;
import com.Aj.Entity.Payment;
import com.Aj.Entity.PaymentStatus;
import com.Aj.Exception.BadRequestException;
import com.Aj.Repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

@Service
public class PaymentService {

	private final BookingService bookingService;
	private final PaymentRepository paymentRepository;
	private final String razorpayKeyId;
	private final String razorpaySecret;

	public PaymentService(BookingService bookingService,
			PaymentRepository paymentRepository,
			@Value("${app.razorpay.key-id:rzp_test_Obz1IQlDtOvh1b}") String razorpayKeyId,
			@Value("${app.razorpay.key-secret:emEgTjBai7QkbElaxGGDUZc5}") String razorpaySecret) {
		this.bookingService = bookingService;
		this.paymentRepository = paymentRepository;
		this.razorpayKeyId = razorpayKeyId;
		this.razorpaySecret = razorpaySecret;
	}

	@Transactional
	public String createOrder(int bookingId, String email) throws Exception {
		Booking booking = bookingService.findOwnedBooking(bookingId, email);
		if (booking.getStatus() != BookingStatus.PENDING_PAYMENT) {
			throw new BadRequestException("Booking is not eligible for payment");
		}

		Payment payment = booking.getPayment();
		if (payment == null) {
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

		return order.toString();
	}

	@Transactional
	public void verifyAndConfirm(PaymentVerifyRequest request, String email) throws Exception {
		Booking booking = bookingService.findOwnedBooking(request.getBookingId(), email);
		Payment payment = booking.getPayment();
		if (payment == null) {
			throw new BadRequestException("Payment record missing for booking");
		}
		if (!request.getRazorpayOrderId().equals(payment.getRazorpayOrderId())) {
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
			throw new BadRequestException("Payment signature verification failed");
		}

		payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
		payment.setRazorpaySignature(request.getRazorpaySignature());
		payment.setStatus(PaymentStatus.SUCCESS);
		booking.setStatus(BookingStatus.CONFIRMED);

		paymentRepository.save(payment);
	}

	public String getRazorpayKeyId() {
		return razorpayKeyId;
	}
}
