package io.BookingSystem.BusBookingSystem.service;

import io.BookingSystem.BusBookingSystem.Mapper.UserMapper;
import io.BookingSystem.BusBookingSystem.Security.JWTUtils;
import io.BookingSystem.BusBookingSystem.dto.ForgetDto;
import io.BookingSystem.BusBookingSystem.dto.LoginDto;
import io.BookingSystem.BusBookingSystem.dto.UserDto;
import io.BookingSystem.BusBookingSystem.entity.User;
import io.BookingSystem.BusBookingSystem.exceptions.BookingException;
import io.BookingSystem.BusBookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImplementation implements UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JWTUtils jwtUtils;
	private final UserDetailsService userDetailsService;

	@Autowired
	public UserServiceImplementation(UserRepository userRepository, UserMapper userMapper,
			@Lazy PasswordEncoder passwordEncoder, @Lazy AuthenticationManager authenticationManager, JWTUtils jwtUtils,
			UserDetailsService userDetailsService) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
	}

	private static final int maximumAttempts = 5;
	private static final long lockPeriodTime = 30;

	@Override
	public UserDto addUserDetails(UserDto userDto) {
		// checking for the duplicate user
		if (userRepository.findByEmail(userDto.getEmail()) != null
				|| userRepository.findByUserName(userDto.getUserName()) != null) {
			throw new BookingException("User already exists", HttpStatus.CONFLICT);
		}
		// Encrypt the password
		String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
		User user = new User();
		user.setUserName(userDto.getUserName());
		user.setEmail(userDto.getEmail());
		user.setPassword(encryptedPassword); // Set encrypted password
		user.setRole(userDto.getRole());
		// Save the user entity to the database
		user = userRepository.save(user);
		// Converting the saved user entity back to UserDto
		UserDto savedUserDto = new UserDto();
		savedUserDto.setUserName(user.getUserName());
		savedUserDto.setEmail(user.getEmail());
		savedUserDto.setRole(user.getRole());
		return savedUserDto;
	}

	public String loginUser(LoginDto loginDto) {
		User user = userRepository.findByEmail(loginDto.getEmail());

		if (user.getLockTime() != null
				&& user.getLockTime().isAfter(LocalDateTime.now().minusMinutes(lockPeriodTime))) {
			throw new BookingException("Account is locked. Try again later after 30 minutes .", HttpStatus.LOCKED);
		}
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
			resetFailedAttempts(user);
		} catch (BadCredentialsException e) {
			increaseFailedAttempts(user);
			throw new BookingException("Invalid credentials", HttpStatus.UNAUTHORIZED);

		} catch (Exception e) {
			throw new BookingException("An error occurred while logging in", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());
		// log.info("Generated token: >>>>> " +
		// jwtUtils.generateTokenFromUsername(userDetails));
		// Generate JWT token with 5 minutes expiration time
		String jwtToken = jwtUtils.generateTokenFromUsername(userDetails, 5 * 60 * 1000);
		// Generate refresh token with 10 minutes expiration time
		String refreshToken = jwtUtils.generateRefreshTokenFromUsername(userDetails, 10 * 60 * 1000);
		// Return both tokens
		return jwtToken + "\n" + refreshToken;
	}

	@Override
	public String forgetPasswords(ForgetDto forgetDto) {
		User user = userRepository.findByUserName(forgetDto.getUserName());
		if (user == null || !user.getEmail().equals(forgetDto.getEmail())) {
			throw new BookingException("Not found the username or email ", HttpStatus.NOT_FOUND);
		}
		user.setPassword(passwordEncoder.encode(forgetDto.getNewPassword()));
		userRepository.save(user);
		return "Password changed Successfully ";
	}

	private void increaseFailedAttempts(User user) {
		int newFailedAttempts = user.getFailedAttempts() + 1;
		user.setFailedAttempts(newFailedAttempts);
		if (newFailedAttempts >= maximumAttempts) {
			user.setLockTime(LocalDateTime.now());
		}
		userRepository.save(user);
	}

	private void resetFailedAttempts(User user) {
		user.setFailedAttempts(0);
		user.setLockTime(null);
		userRepository.save(user);
	}
}