package io.BookingSystem.BusBookingSystem.dto;

import io.BookingSystem.BusBookingSystem.entity.Seats;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfoDto {

	@NotNull(message = "Number of passengers is required")
	@Schema(description = "Selected bus by user  for booking the seats", example = "10")
	private int busId;
	@NotNull(message = "Number of passengers is required")
	@Schema(description = "list of seats selected by the user from the bus", example = "13,16,15")
	private List<Seats> seats;
}
