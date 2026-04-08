package com.Aj.travel;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.Aj.travel.Repository.UserRepository;
import com.Aj.travel.Service.PaymentService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WebSecurityAndControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@MockBean
	private PaymentService paymentService;

	@Test
	void createOrderAllowsAuthenticatedUserAndDelegatesToService() throws Exception {
		when(paymentService.createOrder(12, "traveler@example.com")).thenReturn("{\"id\":\"order_123\"}");

		mockMvc.perform(post("/createOrder")
				.with(user("traveler@example.com").authorities(new SimpleGrantedAuthority("USER")))
				.with(csrf())
				.contentType("application/json")
				.content("{\"bookingId\":12}"))
				.andExpect(status().isOk())
				.andExpect(content().string("{\"id\":\"order_123\"}"));

		verify(paymentService).createOrder(12, "traveler@example.com");
	}

	@Test
	void verifyPaymentReturnsConfirmationPayloadForAuthenticatedUser() throws Exception {
		doNothing().when(paymentService).verifyAndConfirm(any(), eq("traveler@example.com"));

		mockMvc.perform(post("/payment/verify")
				.with(user("traveler@example.com").authorities(new SimpleGrantedAuthority("USER")))
				.with(csrf())
				.contentType("application/json")
				.content("""
						{
						  "bookingId": 5,
						  "razorpayOrderId": "order_123",
						  "razorpayPaymentId": "pay_456",
						  "razorpaySignature": "signature_789"
						}
						"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("verified"))
				.andExpect(jsonPath("$.bookingId").value(5));
	}

	@Test
	void userRegistrationEndpointCreatesUserAndRedirectsToLogin() throws Exception {
		mockMvc.perform(post("/Aj/Resistation_process_User")
				.with(csrf())
				.param("userName1", "New User")
				.param("email", "registered@example.com")
				.param("userPassword", "Password123!")
				.param("userPhoneNO", "9999999999")
				.param("userAddresh", "Test Address"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login"));

		org.junit.jupiter.api.Assertions.assertTrue(userRepository.findByEmail("registered@example.com").isPresent());
	}

	@Test
	void anonymousUserIsRedirectedToLoginForProtectedPackagesPage() throws Exception {
		mockMvc.perform(get("/packages"))
				.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "http://localhost/login"));
	}

	@Test
	void regularUserCannotAccessAdminDashboard() throws Exception {
		mockMvc.perform(get("/Admin_login")
				.with(user("traveler@example.com").authorities(new SimpleGrantedAuthority("USER"))))
				.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.error").value("Forbidden"))
				.andExpect(jsonPath("$.message").value("Access Denied"))
				.andExpect(jsonPath("$.path").value("/Admin_login"));
	}

	@Test
	void adminCannotAccessUserOnlyPackagesPage() throws Exception {
		mockMvc.perform(get("/packages")
				.with(user("admin@example.com").authorities(new SimpleGrantedAuthority("ADMIN"))))
				.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.status").value(403))
				.andExpect(jsonPath("$.error").value("Forbidden"))
				.andExpect(jsonPath("$.message").value("Access Denied"))
				.andExpect(jsonPath("$.path").value("/packages"));
	}

	@Test
	void adminCanAccessAdminDashboard() throws Exception {
		mockMvc.perform(get("/Admin_login")
				.with(user("admin@example.com").authorities(new SimpleGrantedAuthority("ADMIN"))))
				.andExpect(status().isOk())
				.andExpect(view().name("/Admin/LoginSuccess"));
	}
}

