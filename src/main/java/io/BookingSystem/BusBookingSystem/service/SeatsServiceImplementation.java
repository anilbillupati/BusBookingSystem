package io.BookingSystem.BusBookingSystem.service;

import io.BookingSystem.BusBookingSystem.Mapper.SeatsMapper;
import io.BookingSystem.BusBookingSystem.dto.SeatsDto;
import io.BookingSystem.BusBookingSystem.entity.BusDetails;
import io.BookingSystem.BusBookingSystem.entity.Seats;
import io.BookingSystem.BusBookingSystem.exceptions.BookingException;
import io.BookingSystem.BusBookingSystem.repository.BusDetailsRepository;
import io.BookingSystem.BusBookingSystem.repository.SeatsRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatsServiceImplementation implements SeatsService {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(SeatsServiceImplementation.class);
	@Autowired
	private SeatsRepository seatsRepository;
	@Autowired
	private SeatsMapper seatsMapper;
	@Autowired
	BusDetailsRepository busDetailsRepository;

	@Transactional
	public void bookSeats(List<Seats> selectedSeats, int busId) {
		selectedSeats.forEach(seat -> {
			seat.setAvailable(false);
		});
		seatsRepository.saveAll(selectedSeats);
	}

	@Override
	public List<SeatsDto> availableSeats(int busId) {
		BusDetails busDetails = busDetailsRepository.findById(busId)
				.orElseThrow(() -> new BookingException("Bus id not found " + busId, HttpStatus.NOT_FOUND));
		List<Seats> seatsList = seatsRepository.findAvailableSeatsByBusId(busId);
		// log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>: " + seatsList);
		return seatsList.stream().map(seatsMapper::seatsToSeatsDto).collect(Collectors.toList());
	}

	@Override
	public List<SeatsDto> bookedSeats(int busId) {
		BusDetails busDetails = busDetailsRepository.findById(busId)
				.orElseThrow(() -> new BookingException("Bus id not found " + busId, HttpStatus.NOT_FOUND));
		List<Seats> bookedSeatsList = seatsRepository.findBookedSeatsByBusId(busId);
		return bookedSeatsList.stream().map(seatsMapper::seatsToSeatsDto).collect(Collectors.toList());
	}

}
