
package io.BookingSystem.BusBookingSystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
	@Schema(description = "After the successful payment a unique booking id is generated", example = "712430")
	private int bookingId;
	@Schema(description = "In which bus the seats are selected ", example = "10")
	private int busId;
	@Schema(description = "User Id who booked seat", example = "12")
	private int userId;
	@Schema(description = "booked seats ", example = "15,18")
	private List<Integer> selectedSeats;
	@Schema(description = "uniquely generated payment id", example = "126456")
	private int paymentId;
	@Schema(description = "booking time", example = "2024-08-27T18:11:57.629")
	private LocalDateTime bookingDateTime;
}
