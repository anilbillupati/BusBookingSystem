package io.BookingSystem.BusBookingSystem.controller;

import io.BookingSystem.BusBookingSystem.dto.BookingDto;
import io.BookingSystem.BusBookingSystem.dto.PaymentDto;
import io.BookingSystem.BusBookingSystem.dto.PaymentInfoDto;
import io.BookingSystem.BusBookingSystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@PreAuthorize("hasAuthority('User')")
	@PostMapping("/generatePaymentDetails")
	public ResponseEntity<PaymentDto> paymentResponse(@RequestBody PaymentInfoDto paymentInfoDto) {
		PaymentDto paymentDto = bookingService.generatePaymentDetails(paymentInfoDto);
		return ResponseEntity.ok(paymentDto);
	}

	@PreAuthorize("hasAuthority('User')")
	@PostMapping("/bookingSuccess")
	public ResponseEntity<BookingDto> bookingDetails(@RequestBody PaymentDto paymentDto) {
		BookingDto bookingDto = bookingService.booking(paymentDto);
		return ResponseEntity.ok(bookingDto);
	}
}
