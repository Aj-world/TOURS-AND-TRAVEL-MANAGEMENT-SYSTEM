package com.aj.travel.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Long id;
    private Long bookingId;
    private BigDecimal amount;
    private String paymentMethod;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String status;
    private LocalDateTime paidAt;
}
