package io.BookingSystem.BusBookingSystem.Mapper;

import io.BookingSystem.BusBookingSystem.dto.BookingDto;
import io.BookingSystem.BusBookingSystem.entity.BookingDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {

	BookingDto bookingDetailsToBookingDto(BookingDetails bookingDetails);

	BookingDetails bookingDtoToBookingDetails(BookingDto bookingDto);
}
