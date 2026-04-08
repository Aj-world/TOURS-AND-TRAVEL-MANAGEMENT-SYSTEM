package com.Aj.travel.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Aj.travel.Entity.User;
import com.Aj.travel.Entity.UserRole;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);

	@Query("select case when count(u) > 0 then true else false end from User u where u.UserRole = :role")
	boolean existsByRole(@Param("role") UserRole role);
}

