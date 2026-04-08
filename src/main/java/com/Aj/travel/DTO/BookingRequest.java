package com.aj.travel.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getGuests() {
		return guests;
	}

	public void setGuests(int guests) {
		this.guests = guests;
	}

	public LocalDate getArivalDate() {
		return ArivalDate;
	}

	public void setArivalDate(LocalDate arivalDate) {
		ArivalDate = arivalDate;
	}

	public LocalDate getLeavingDate() {
		return LeavingDate;
	}

	public void setLeavingDate(LocalDate leavingDate) {
		LeavingDate = leavingDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}

