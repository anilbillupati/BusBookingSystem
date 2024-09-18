package io.BookingSystem.BusBookingSystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class SeatsDto {

	@Schema(description = "Seat id is unique ", example = "1")
	private int seatId;
	// bus ID from BusDetails
	@Schema(description = "Bus id is unique ", example = "10")
	private int busId;
	@Schema(description = "If not booked it is false else it is true ", example = "True")
	private boolean isAvailable;

}
