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

public class PaymentDto {
	// if the payment is success the details stored in the table
	// else return payment failed message // need to do it
	@Schema(description = "Payment is uniquely generated", example = "1232145")
	private int paymentId;
	@Schema(description = "Bus id comes from the users interest ", example = "10")
	private int busId;
	@Schema(description = "User id comes from the user login", example = "123")
	private int userId;
	@Schema(description = "Seats selected by the user", example = "12,13,22")
	private List<Integer> selectedSeats;
	@Schema(description = "No of seats selected * fare ", example = "2000")
	private int amount;
	// -> internet banking ,upi , cash
	@Schema(description = "Modes of payment  cash/UPI/Card", example = "Card")
	private String paymentType;
	@Schema(description = "Payment successful time ", example = "2024-08-27T17:58:22.584")
	private LocalDateTime paymentDateTime;
	@Schema(description = "Success or failed transaction", example = "True")
	private boolean paymentStatus; // wants to generate it randomly

}
