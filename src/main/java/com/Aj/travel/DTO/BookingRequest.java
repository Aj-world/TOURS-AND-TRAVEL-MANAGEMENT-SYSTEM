package com.aj.travel.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
public class BookingRequest {

	@NotBlank
	@Size(max = 120)
	private String name;

	@NotBlank
	@Email
	private String email;

	@Pattern(regexp = "^[0-9]{10,15}$")
	private String phone;
	private String address;

	@NotBlank
	@Size(max = 120)
	private String location;

	@NotNull
	@Min(1)
	private Integer guests;

	@NotNull
	@Future
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate ArivalDate;

	@NotNull
	@FutureOrPresent
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate LeavingDate;

	@NotNull
	@Min(1)
	private Integer price;

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

	public Integer getGuests() {
		return guests;
	}

	public void setGuests(Integer guests) {
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}

