package io.BookingSystem.BusBookingSystem.service;

import io.BookingSystem.BusBookingSystem.Mapper.BookingMapper;
import io.BookingSystem.BusBookingSystem.Mapper.SeatsMapper;
import io.BookingSystem.BusBookingSystem.dto.BookingDto;
import io.BookingSystem.BusBookingSystem.dto.PaymentDto;
import io.BookingSystem.BusBookingSystem.dto.PaymentInfoDto;
import io.BookingSystem.BusBookingSystem.entity.*;
import io.BookingSystem.BusBookingSystem.exceptions.BookingException;
import io.BookingSystem.BusBookingSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

//
@Service
public class BookingServiceImplementation implements BookingService {
//                    ->load the payment info details like bus id, list < seats >
//                    ->fetch the username from for the authentication  are logged in using the token
//                    ->check for the seats are availability if seats are available
//                    ->generate the payment deatils
//                    ->mark the booked seats in corresponding bus(based on bus id )  to bookedSeats in real time

	@Autowired
	private SeatsRepository seatsRepository;

	@Autowired
	private BusDetailsRepository busDetailsRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PaymentDetailsRepository paymentDetailsRepository;

	@Autowired
	private SeatsMapper seatsMapper;

	@Autowired
	private BookingDetailsRepository bookingDetailsRepository;

	@Autowired
	private BookingMapper bookingMapper;

	public PaymentDto generatePaymentDetails(PaymentInfoDto paymentInfoDto) {
		// Load payment info details
		int busId = paymentInfoDto.getBusId();
		List<Seats> selectedSeats = paymentInfoDto.getSeats();

		// Fetch username from authentication
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		User user = userRepository.findByEmail(currentUserName);

		// Check if the seats are available
		List<Seats> availableSeats = seatsRepository.findAvailableSeatsByBusId(busId);
		List<Integer> availableSeatIds = availableSeats.stream().map(Seats::getSeatId).collect(Collectors.toList());

		if (availableSeatIds.containsAll(selectedSeats.stream().map(Seats::getSeatId).collect(Collectors.toList()))) {

			// payment details and save
			PaymentDetails paymentDetails = new PaymentDetails();
			paymentDetails.setBusDetails(busDetailsRepository.findById(busId).orElse(null));
			paymentDetails.setUser(user);
			paymentDetails.setSelectedSeats(selectedSeats.stream().map(Seats::getSeatId).collect(Collectors.toList()));
			paymentDetails.setAmount(calculateAmount(selectedSeats, busId));
			paymentDetails.setPaymentDateTime(LocalDateTime.now());
			paymentDetails.setPaymentStatus(true);
			String paymentType = randomPaymentType();
			paymentDetails.setPaymentType(paymentType);
			paymentDetailsRepository.save(paymentDetails);

			// making selected seats as booked
			selectedSeats.forEach(seat -> seat.setAvailable(false));
			BusDetails busDetails = busDetailsRepository.findById(busId).orElseThrow(null);
			selectedSeats.forEach(seat -> seat.setBusDetails(busDetails));
			seatsRepository.saveAll(selectedSeats);

			// storing in the dto
			PaymentDto paymentDto = new PaymentDto();
			paymentDto.setPaymentId(paymentDetails.getPaymentId());
			paymentDto.setBusId(busId);
			paymentDto.setUserId(paymentDetails.getUser().getId());
			paymentDto.setSelectedSeats(selectedSeats.stream().map(Seats::getSeatId).collect(Collectors.toList()));
			paymentDto.setAmount(paymentDetails.getAmount());
			paymentDto.setPaymentDateTime(paymentDetails.getPaymentDateTime());
			paymentDto.setPaymentStatus(true);
			paymentDto.setPaymentType(paymentType);
			return paymentDto;
		} else {
			throw new BookingException("Seats are already booked.", HttpStatus.BAD_REQUEST);
		}
	}

	// -> booking details includes user id ,bus id selected seats
	@Override
	public BookingDto booking(PaymentDto paymentDto) {
//          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//          String currentUserName = authentication.getName();
//          User user = userRepository.findByEmail(currentUserName);
		BookingDetails bookingDetails = new BookingDetails();

		// Get bus details from repository
		BusDetails bus = busDetailsRepository.findById(paymentDto.getBusId())
				.orElseThrow(() -> new BookingException("Bus not found", HttpStatus.NOT_FOUND));
		bookingDetails.setBusDetails(bus);

		// Get user details from repository
		User user = userRepository.findById(paymentDto.getUserId())
				.orElseThrow(() -> new BookingException("User not found", HttpStatus.NOT_FOUND));
		bookingDetails.setUser(user);

		// Get payment details from repository
		PaymentDetails payment = paymentDetailsRepository.findById(paymentDto.getPaymentId())
				.orElseThrow(() -> new BookingException("Payment not found", HttpStatus.NOT_FOUND));
		bookingDetails.setPaymentDetails(payment);

		// Set selected seats
		bookingDetails.setSelectedSeats(paymentDto.getSelectedSeats());

		bookingDetails.setBookingDateTime(LocalDateTime.now());
		// Save booking details
		bookingDetailsRepository.save(bookingDetails);

		// Create BookingDto from BookingDetails
		BookingDto bookingDto = new BookingDto();
		bookingDto.setBookingId(bookingDetails.getBookingId());
		bookingDto.setBusId(bookingDetails.getBusDetails().getBusId());
		bookingDto.setUserId(bookingDetails.getUser().getId());
		bookingDto.setPaymentId(bookingDetails.getPaymentDetails().getPaymentId());
		bookingDto.setSelectedSeats(bookingDetails.getSelectedSeats());
		bookingDto.setBookingDateTime(bookingDetails.getBookingDateTime());

		return bookingDto;
	}

	// -> calculate the amount =>(Fare from the bus details * no of seats selected)
	// -> store this in the payment details
	private int calculateAmount(List<Seats> selectedSeats, int busId) {
		BusDetails newbusDetails = busDetailsRepository.findById(busId)
				.orElseThrow(() -> new BookingException("Bus not found", HttpStatus.NOT_FOUND));
		return selectedSeats.size() * newbusDetails.getFare();
	}

	private String randomPaymentType() {
		String[] paymentTypes = { "Net Banking", "Card", "UPI" };
		Random random = new Random();
		return paymentTypes[random.nextInt(paymentTypes.length)];
	}
}
