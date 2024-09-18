package io.BookingSystem.BusBookingSystem.Mapper;

import io.BookingSystem.BusBookingSystem.dto.SeatsDto;
import io.BookingSystem.BusBookingSystem.entity.Seats;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatsMapper {

	@Mapping(source = "busDetails.busId", target = "busId")
	SeatsDto seatsToSeatsDto(Seats seats);

	@Mapping(source = "busId", target = "busDetails.busId")
	Seats seatsDtoToSeats(SeatsDto seatsDto);

	// Custom method to map a list of Seats to a list of SeatsDto
	List<SeatsDto> seatsToSeatsDtoList(List<Seats> seats);

}
