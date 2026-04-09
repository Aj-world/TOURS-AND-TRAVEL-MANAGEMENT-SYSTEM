package com.aj.travel.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
public class CreateOrderRequest {
	@NotNull
	@Min(1)
	private Integer bookingId;

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}
}

