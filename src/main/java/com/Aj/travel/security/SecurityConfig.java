package com.aj.travel.security;

import static com.aj.travel.constants.ApiPaths.AUTH_LOGIN;
import static com.aj.travel.constants.ApiPaths.AUTH_REGISTER_ADMIN;
import static com.aj.travel.constants.ApiPaths.AUTH_REGISTER_USER;
import static com.aj.travel.constants.ApiPaths.CSS_ALL;
import static com.aj.travel.constants.ApiPaths.ERROR;
import static com.aj.travel.constants.ApiPaths.H2_CONSOLE;
import static com.aj.travel.constants.ApiPaths.IMAGES_ALL;
import static com.aj.travel.constants.ApiPaths.JS_ALL;
import static com.aj.travel.constants.ApiPaths.LOGIN;
import static com.aj.travel.constants.ApiPaths.LOGOUT;
import static com.aj.travel.constants.ApiPaths.PAYMENTS_ALL;
import static com.aj.travel.constants.ApiPaths.PAYMENT_ORDERS_FULL;
import static com.aj.travel.constants.ApiPaths.ROOT;
import static com.aj.travel.constants.SecurityConstants.ADMIN;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				.csrf(csrf -> csrf.ignoringRequestMatchers(
						new AntPathRequestMatcher(H2_CONSOLE),
						new AntPathRequestMatcher(PAYMENT_ORDERS_FULL, HttpMethod.POST.name()),
						new AntPathRequestMatcher(PAYMENTS_ALL)
				))

				.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

				.authorizeHttpRequests(auth -> auth
						.requestMatchers(new AntPathRequestMatcher(LOGIN)).permitAll()
						.requestMatchers(new AntPathRequestMatcher(AUTH_LOGIN)).permitAll()
						.requestMatchers(new AntPathRequestMatcher(AUTH_REGISTER_USER)).permitAll()
						.requestMatchers(new AntPathRequestMatcher(ERROR)).permitAll()
						.requestMatchers(new AntPathRequestMatcher(CSS_ALL)).permitAll()
						.requestMatchers(new AntPathRequestMatcher(JS_ALL)).permitAll()
						.requestMatchers(new AntPathRequestMatcher(IMAGES_ALL)).permitAll()
						.requestMatchers(new AntPathRequestMatcher(H2_CONSOLE)).permitAll()

						.requestMatchers(new AntPathRequestMatcher(AUTH_REGISTER_ADMIN)).hasRole(ADMIN)

						.anyRequest().authenticated()
				)

				.formLogin(form -> form
						.loginPage(LOGIN)
						.loginProcessingUrl(LOGIN)
						.defaultSuccessUrl(ROOT, true)
						.permitAll()
				)

				.logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT))
						.permitAll()
				)

				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				);

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
