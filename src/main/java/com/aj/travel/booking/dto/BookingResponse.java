package com.aj.travel.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long id;
    private Long userId;
    private Long packageId;
    private Integer guestCount;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime bookingDate;
}
