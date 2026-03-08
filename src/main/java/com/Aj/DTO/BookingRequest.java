package com.Aj.DTO;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequest {

	@NotBlank
	private String name;

	@NotBlank
	private String email;

	private String phone;
	private String address;

	@NotBlank
	private String location;

	@Min(1)
	private int guests;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ArivalDate;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate LeavingDate;

	@Min(1)
	private int price;
}
