package io.BookingSystem.BusBookingSystem.Mapper;

import io.BookingSystem.BusBookingSystem.dto.BusDto;
import io.BookingSystem.BusBookingSystem.entity.BusDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BusDetailsMapper {
	@Mapping(source = "busId", target = "id")
	BusDto busDetailsToBusDto(BusDetails busDetails);

	BusDetails busDtoToBusDetails(BusDto busDto);

	void updateBusDetails(BusDto busDto, @MappingTarget BusDetails busDetails);

}
