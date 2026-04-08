package com.Aj.travel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.Aj.travel.Entity.User;
import com.Aj.travel.Entity.UserRole;
import com.Aj.travel.Exception.BadRequestException;
import com.Aj.travel.Service.RegistrationService;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RegistrationIntegrationTest {

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void registerStoresEncodedPasswordAndRole() {
		User savedUser = registrationService.register(user("newuser@example.com"), UserRole.USER);

		assertEquals(UserRole.USER, savedUser.getUserRole());
		assertTrue(passwordEncoder.matches("Password123!", savedUser.getUserPassword()));
	}

	@Test
	void registerRejectsDuplicateEmail() {
		registrationService.register(user("duplicate@example.com"), UserRole.USER);

		assertThrows(BadRequestException.class,
				() -> registrationService.register(user("duplicate@example.com"), UserRole.USER));
	}

	private User user(String email) {
		User user = new User();
		user.setUserName1("Registration User");
		user.setEmail(email);
		user.setUserPassword("Password123!");
		user.setUserPhoneNO("9999999999");
		user.setUserAddresh("Test Address");
		return user;
	}
}

