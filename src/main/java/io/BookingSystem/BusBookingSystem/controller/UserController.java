package io.BookingSystem.BusBookingSystem.controller;

import io.BookingSystem.BusBookingSystem.Security.JWTUtils;
import io.BookingSystem.BusBookingSystem.dto.ForgetDto;
import io.BookingSystem.BusBookingSystem.dto.LoginDto;
import io.BookingSystem.BusBookingSystem.dto.UserDto;
import io.BookingSystem.BusBookingSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private JWTUtils jwtUtils;

	@PostMapping("/register")
	public ResponseEntity<UserDto> userRegistration(@Valid @RequestBody UserDto userDto) {
		UserDto user1 = userService.addUserDetails(userDto);
		return new ResponseEntity<>(user1, HttpStatus.CREATED);
	}

	// Endpoint for user login
	@PostMapping("/login")
	public ResponseEntity<String> userLogin(@Valid @RequestBody LoginDto loginDto) {
		// System.out.println("Controller class
		// "+loginDto.getEmail()+"password"+loginDto.getPassword());
		String response = userService.loginUser(loginDto);

		if (!ObjectUtils.isEmpty(response)) {
			// System.out.print("INSIDE IF ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			// System.out.print("INSIDE ELSE ");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("/forgetPassword")
	public ResponseEntity<String> updateForgetPassword(@Valid @RequestBody ForgetDto forgetDto) {

		String response = userService.forgetPasswords(forgetDto);
		// System.out.println("before the Controller if");
		if (!ObjectUtils.isEmpty(response))
			return new ResponseEntity<>(response, HttpStatus.OK);

		else {
			// System.out.println("after the Controller else");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/logOut")
	public String userLogout() {
		return "Logged out successfully ";
	}
}
