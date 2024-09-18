package io.BookingSystem.BusBookingSystem.service;

import io.BookingSystem.BusBookingSystem.dto.BusDto;

public interface BusDetailsService {

	BusDto addBusDetails(BusDto busDto);

	void updateBusDetails(BusDto busDto);
}
