package com.Aj.travel.DTO;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CreateOrderRequest {
	@Min(1)
	private int bookingId;
}

