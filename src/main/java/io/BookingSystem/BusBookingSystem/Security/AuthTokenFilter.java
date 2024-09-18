package io.BookingSystem.BusBookingSystem.Security;

import io.BookingSystem.BusBookingSystem.exceptions.BookingException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;

//once per request is to intercept the incoming request and sending it to the
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

	// autowiring the util classes
	@Autowired
	private JWTUtils jwtUtils;
	@Autowired
	private UserDetailsService userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.debug("AuthTokenFilter called for URI: {}", request.getRequestURI());
		try {

			if (isEndpointWithoutJwt(request)) {
				filterChain.doFilter(request, response);
				return;
			}

			// extract the jwt token
			String jwt = parseJwt(request);
			if (jwt == null) {
				// If no JWT token is present, throw an error or return a 401 Unauthorized
				// response
				logger.error("No JWT token found in the request");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}

			// this condition checks for the weather the token is validated
			// i.e user credentials can have authorization or not and the jwt is can not be null
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);
				// extracts the username
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				// Check if the token is a refresh token
				if (jwtUtils.isRefreshToken(jwt)) {
					// Generate a new JWT token with the same username and authorities
					String newJwtToken = jwtUtils.generateTokenFromUsername(userDetails, 10 * 60 * 1000);
					response.setHeader("Authorization", "Bearer " + newJwtToken);
				}

				// authentication token can have what can user access with the token
				// suppose a user can not access the admin
				// suppose in my pass team everyone can not have access to the production deployment
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				logger.debug("Roles from JWT: {}", userDetails.getAuthorities());

				// making the token more strong by adding the current session details request info etc..
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				// If the JWT token is invalid, throw an error or return a 401 Unauthorized response
				logger.error("Invalid JWT token");
				throw new BookingException("invalid jwt token :", HttpStatus.UNAUTHORIZED);
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
			}
		} catch (Exception e) {
			throw new BookingException("invalid jwt token :", HttpStatus.UNAUTHORIZED);
		}
		filterChain.doFilter(request, response);
	}

	// this method extracts the token from the users http request
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (!ObjectUtils.isEmpty(headerAuth)) {
			String[] authParts = headerAuth.split(" ");
			if (authParts.length == 2 && authParts[0].equals("Bearer")) {
				// Return the JWT token
				return authParts[1];
			}
		}
		return null;
	}

	private boolean isEndpointWithoutJwt(HttpServletRequest request) {
		String requestURL = request.getRequestURI();
		return requestURL.startsWith("/login") || requestURL.startsWith("/logOut") || requestURL.startsWith("/register")
				|| requestURL.startsWith("/forgetPassword") || requestURL.startsWith("/actuator")
				|| requestURL.startsWith("/v3/api-docs/") || requestURL.startsWith("/swagger-ui/") ||
				requestURL.matches("/swagger-ui/.*");
	}
}
