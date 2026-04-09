package com.aj.travel.component;

import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.aj.travel.entity.User;
import com.aj.travel.entity.UserRole;
import com.aj.travel.repository.UserRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(AdminInitializer.class);
	private static final String PASSWORD_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789!@#$%^&*";

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final String adminName;
	private final String adminEmail;
	private final String adminPassword;

	public AdminInitializer(
			UserRepository userRepository,
			PasswordEncoder passwordEncoder,
			@Value("${bootstrap.admin.name:Default Admin}") String adminName,
			@Value("${bootstrap.admin.email:admin@travel.local}") String adminEmail,
			@Value("${bootstrap.admin.password:}") String adminPassword) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.adminName = adminName;
		this.adminEmail = adminEmail;
		this.adminPassword = adminPassword;
	}

	@Override
	public void run(String... args) {
		if (userRepository.existsByRole(UserRole.ADMIN)) {
			return;
		}

		String rawPassword = hasText(adminPassword) ? adminPassword : generatePassword(16);

		User admin = new User();
		admin.setUserName(adminName);
		admin.setEmail(adminEmail);
		admin.setUserPassword(passwordEncoder.encode(rawPassword));
		admin.setUserRole(UserRole.ADMIN);
		admin.setUserPhoneNo("N/A");
		admin.setUserAddress("Bootstrap Admin");

		userRepository.save(admin);

		if (hasText(adminPassword)) {
			log.warn("No ADMIN user found. Created bootstrap admin with email '{}'.", adminEmail);
		} else {
			log.warn("No ADMIN user found. Created bootstrap admin with email '{}'. Set BOOTSTRAP_ADMIN_PASSWORD to override the generated password.", adminEmail);
		}
	}

	private boolean hasText(String value) {
		return value != null && !value.isBlank();
	}

	private String generatePassword(int length) {
		SecureRandom secureRandom = new SecureRandom();
		StringBuilder password = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			password.append(PASSWORD_CHARS.charAt(secureRandom.nextInt(PASSWORD_CHARS.length())));
		}
		return password.toString();
	}
}
