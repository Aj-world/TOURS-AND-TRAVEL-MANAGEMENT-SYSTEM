package com.aj.travel.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "bookings")
public class Booking {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "book_id")
	private int bookId;

	@Column(nullable = false)
	private int guest;

	@Column(name = "arrival_date", nullable = false)
	private LocalDate arrivalDate;

	@Column(name = "leaving_date", nullable = false)
	private LocalDate leavingDate;

	@Column(name = "total_amount", nullable = false)
	private int totalAmount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private BookingStatus status = BookingStatus.PENDING_PAYMENT;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "package_id", unique = true)
	private Package travelPackage;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
	private Payment payment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getGuest() {
		return guest;
	}

	public void setGuest(int guest) {
		this.guest = guest;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public LocalDate getLeavingDate() {
		return leavingDate;
	}

	public void setLeavingDate(LocalDate leavingDate) {
		this.leavingDate = leavingDate;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public Package getTravelPackage() {
		return travelPackage;
	}

	public void setTravelPackage(Package travelPackage) {
		this.travelPackage = travelPackage;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

