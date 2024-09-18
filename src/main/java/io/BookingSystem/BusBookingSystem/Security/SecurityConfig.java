package io.BookingSystem.BusBookingSystem.Security;

import io.BookingSystem.BusBookingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private UserService userService;

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private AuthTokenFilter authTokenFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/register", "/login", "/forgetPassword", "/logOut", "/actuator/**",
								"/v3/api-docs/**", "/swagger-ui/**") // Simplified Swagger UI endpoints
						.permitAll().requestMatchers("/").hasAuthority("Admin").requestMatchers("/")
						.hasAuthority("User").anyRequest().authenticated() // Require authentication for all other
																			// requests
				).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use
						// stateless// sessions
				).addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
