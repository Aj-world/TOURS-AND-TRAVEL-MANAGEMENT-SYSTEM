package com.Aj.travel.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Aj.travel.Entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);
}

