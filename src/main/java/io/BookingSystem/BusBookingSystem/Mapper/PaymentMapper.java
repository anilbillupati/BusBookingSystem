package io.BookingSystem.BusBookingSystem.Mapper;

import io.BookingSystem.BusBookingSystem.dto.PaymentDto;
import io.BookingSystem.BusBookingSystem.entity.PaymentDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
	@Mapping(source = "busDetails.busId", target = "busId")
	@Mapping(source = "user.id", target = "userId")
	PaymentDto paymentDetailsToPaymentDto(PaymentDetails paymentDetails);

	@Mapping(source = "busId", target = "busDetails.busId")
	@Mapping(source = "userId", target = "user.id")
	PaymentDetails paymentDtoToPaymentDetails(PaymentDto paymentDto);

}
