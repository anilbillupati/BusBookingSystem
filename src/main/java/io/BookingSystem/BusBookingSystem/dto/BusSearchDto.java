package io.BookingSystem.BusBookingSystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class BusSearchDto {

	@NotBlank(message = "Departure city is required")
	@Schema(description = "Mention the departure City :", example = "Hyderabad")
	private String departureCity;

	@NotBlank(message = "Arrival city is required")
	@Schema(description = "Mention the Arrival City :", example = "Nellore")
	private String arrivalCity;

	@NotNull(message = "Departure date is required")
	@Schema(description = "Dare is not old than today", example = "2024-08-24")
	private LocalDate departureDate;

	@NotNull(message = "Number of passengers is required")
	@Schema(description = "No of passengers to travel", example = "2")
	private Integer noOfPassengers;

	@Schema(description = "Sorting the buses by", example = "Fare /DepartureTime/Duration")
	private String sortBy;

	@Schema(description = "Type of the bus ", example = "Ac/Sleeper/Non-Ac")
	private String busType;

	@Schema(description = "Travels name", example = "xyz travels")
	private String busOperator;

}
