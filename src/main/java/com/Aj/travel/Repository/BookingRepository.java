package com.aj.travel.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aj.travel.Entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	@EntityGraph(attributePaths = { "user", "payment", "package1" })
	@Query("select b from Booking b where b.BookId = :bookId and b.user.email = :email")
	Optional<Booking> findOwnedBooking(@Param("bookId") int bookId, @Param("email") String email);
}

