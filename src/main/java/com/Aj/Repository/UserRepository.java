package com.Aj.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Aj.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
}
