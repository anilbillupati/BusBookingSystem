package io.BookingSystem.BusBookingSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponseDto {
	private final String guid;
	private final String message;
	private final String status = "failed";
}
