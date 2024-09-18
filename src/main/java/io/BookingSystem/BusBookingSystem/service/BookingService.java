package io.BookingSystem.BusBookingSystem.service;

import io.BookingSystem.BusBookingSystem.dto.BookingDto;
import io.BookingSystem.BusBookingSystem.dto.PaymentDto;
import io.BookingSystem.BusBookingSystem.dto.PaymentInfoDto;

public interface BookingService {

	// two methods
	// one is for payment response
	PaymentDto generatePaymentDetails(PaymentInfoDto paymentInfoDto);

	// //stores the details like userid,bus Id,seats ids,payment ids etc
	BookingDto booking(PaymentDto paymentDto);
}
