package com.Aj.SecurityConfiguration;

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
						new AntPathRequestMatcher("/h2-console/**"),
						new AntPathRequestMatcher("/createOrder", HttpMethod.POST.name()),
						new AntPathRequestMatcher("/payment/**")
				))

				.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

				.authorizeHttpRequests(auth -> auth
						.requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/Aj/Registion_User")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/Aj/Resistation_process_User")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/Aj11")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/error")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/images/**")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()

						.requestMatchers(new AntPathRequestMatcher("/Aj/Registion_Admin")).hasAuthority("ADMIN")
						.requestMatchers(new AntPathRequestMatcher("/Aj/Resistation_process_Admin")).hasAuthority("ADMIN")

						.anyRequest().authenticated()
				)

				.formLogin(form -> form
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/", true)
						.permitAll()
				)

				.logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
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