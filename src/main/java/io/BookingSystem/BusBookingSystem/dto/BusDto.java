package io.BookingSystem.BusBookingSystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusDto {

	@Schema(description = "Unique number to identify the bus ", example = "1")
	private int id;
	@NotBlank(message = "Departure city is required")
	@Schema(description = "Mention the departure City :", example = "Hyderabad")
	private String departureCity;
	@NotBlank(message = "Destination City required")
	@Schema(description = "Mention the Arrival City :", example = "Nellore")
	private String arrivalCity;
	@NotBlank(message = "Mandatory field ")
	@Schema(description = "Mention the BusOperator :", example = "Garuda")
	private String busOperator;
	@NotBlank(message = "Bus type must be mentioned ")
	@Schema(description = "Ac/Sleeper", example = "Sleeper")
	private String busType;
	@NotNull(message = "DepartureDate  is mandatory")
	@Schema(description = "Dare is not old than today", example = "2024-08-29")
	private LocalDate departureDate;
	@NotNull(message = "Departure time is mandatory")
	@Schema(description = "Departure time ", example = "16:30:00")
	private LocalTime departureTime;
	@NotNull(message = "Arrival time is mandatory")
	@Schema(description = "Arrival time at the destination ", example = "09:00:00")
	private LocalTime arrivalTime;
	@NotNull(message = "Fare of each seat  is mandatory")
	@Schema(description = "fare per seat in Indian rupees", example = "1000")
	private Integer fare;
	@NotNull(message = "Total seats available is mandatory")
	@Schema(description = "No of Seats that a bus can have ", example = "20")
	private Integer totalSeats;
	@Schema(description = "duration of journey in hours", example = "5.0")
	private double duration;

}
