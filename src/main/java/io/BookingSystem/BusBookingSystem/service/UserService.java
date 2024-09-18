package io.BookingSystem.BusBookingSystem.service;

import io.BookingSystem.BusBookingSystem.dto.ForgetDto;
import io.BookingSystem.BusBookingSystem.dto.LoginDto;
import io.BookingSystem.BusBookingSystem.dto.UserDto;

public interface UserService {
	UserDto addUserDetails(UserDto userDto);

	String loginUser(LoginDto loginDto);

	String forgetPasswords(ForgetDto forgetDto);
}
