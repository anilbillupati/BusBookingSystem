package io.BookingSystem.BusBookingSystem.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtils {
	private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

	// secret is sued to signing the

	@Value("${spring.app.jwtSecret}")
	private String jwtSecret;

	@Value("${spring.app.jwtExpirationMs}")
	private long jwtExpirationMs;

	public String generateTokenFromUsername(UserDetails userDetails, long expirationTime) {
		Map<String, Object> claims = new HashMap<>();
		userDetails.getAuthorities().forEach(authority -> claims.put("role", authority.getAuthority()));
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(key(), SignatureAlgorithm.HS256).compact();
	}

	public String generateRefreshTokenFromUsername(UserDetails userDetails, long expirationTime) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("refresh", true); // Add a claim to indicate it's a refresh token
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(key(), SignatureAlgorithm.HS256).compact();
	}

	// getting the jwt token form the request http header
	public String getJwtFromHeader(HttpServletRequest request) {

		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.split(" ")[1].trim(); // Remove Bearer prefix
		}
		return ""; // Return an empty string instead of null
	}
	// generating token from the username comes under jwt utils it is actually a
	// payload part of the token structure
	// because it consists of issuer ,expiration time,subject audience

	public String generateTokenFromUsername(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		userDetails.getAuthorities().forEach(authority -> claims.put("role", authority.getAuthority()));
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(key(), SignatureAlgorithm.HS256)
				// creates a url based on the serialisation
				.compact();
	}

	// extracting the username from the token
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
	}

	// decoding the header in the token part with base64url method
	private Key key() {
		String secret = jwtSecret.trim();
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}

	private Key getSigningKey() {
		String secret = jwtSecret.trim();
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}

	// roles from jwt token
	public String getUserRoleFromJwtToken(String token) {
		return Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token).getBody().get("role", String.class);
	}

	public boolean isRefreshToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
		if (claims.get("refresh") != null) {
			return claims.get("refresh", Boolean.class);
		} else {
			return false;
		}
	}

	// it comes under the auth token filter that is it intercept the request to
	// check the username credentials validates the token
	public boolean validateJwtToken(String authToken) {
		try {
			// System.out.println("Validate");
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		} catch (Exception e) {
			System.out.println("Error validating token: " + e.getMessage());
			logger.error("Exception: {}", e.getMessage());
		}
		return false;
	}
}