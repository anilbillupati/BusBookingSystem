package io.BookingSystem.BusBookingSystem.service;

import io.BookingSystem.BusBookingSystem.dto.SeatsDto;

import java.util.List;

public interface SeatsService {

	// 2 methods one for available seasts and another for the booked seats

	List<SeatsDto> availableSeats(int busId);

	List<SeatsDto> bookedSeats(int busId);

}
