package com.aj.travel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aj.travel.entity.User;
import com.aj.travel.entity.UserRole;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);

	@Query("select case when count(u) > 0 then true else false end from User u where u.userRole = :role")
	boolean existsByRole(@Param("role") UserRole role);
}
