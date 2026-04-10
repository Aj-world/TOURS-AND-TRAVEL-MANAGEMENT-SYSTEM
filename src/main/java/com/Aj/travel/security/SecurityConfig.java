package com.aj.travel.security;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import static com.aj.travel.constants.ApiPaths.AUTH_LOGIN;
import static com.aj.travel.constants.ApiPaths.AUTH_REGISTER_USER;
import static com.aj.travel.constants.ApiPaths.ERROR;
import static com.aj.travel.constants.ApiPaths.H2_CONSOLE;
import static com.aj.travel.constants.SecurityConstants.ADMIN;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
				.csrf(AbstractHttpConfigurer::disable)

				.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								antMatcher(AUTH_LOGIN),
								antMatcher(AUTH_REGISTER_USER),
								antMatcher("/api/packages"),
								antMatcher(HttpMethod.GET, "/api/packages/**")
						).permitAll()
						.requestMatchers(
								antMatcher(H2_CONSOLE),
								antMatcher(ERROR)
						).permitAll()
						.requestMatchers(antMatcher("/admin/**")).hasRole(ADMIN)

						.anyRequest().authenticated()
				)

				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				);

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
