package com.Aj.Dao;
 

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.Aj.Entity.User;
 

public interface UserDao extends JpaRepository<User, Integer> {
	
 
	Optional<User> findByEmail(String email);

	 

}
