package io.BookingSystem.BusBookingSystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusSearchResponseDto {
	@Schema(description = "Bus dto is returned here includes all the bus details ")
	private BusDto bus;
	@Schema(description = "list available seats ", example = "1,2,3,4,5,6,7,8,")
	private List<Integer> availableSeats;
}
