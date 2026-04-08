package com.Aj.travel.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentVerifyRequest {
	@Min(1)
	private int bookingId;

	@NotBlank
	private String razorpayOrderId;

	@NotBlank
	private String razorpayPaymentId;

	@NotBlank
	private String razorpaySignature;
}

