package com.aj.travel.dto;

import jakarta.validation.constraints.Min;
public class CreateOrderRequest {
	@Min(1)
	private int bookingId;

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
}

