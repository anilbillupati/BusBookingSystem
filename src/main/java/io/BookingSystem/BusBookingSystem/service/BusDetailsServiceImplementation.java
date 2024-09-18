package io.BookingSystem.BusBookingSystem.service;

import io.BookingSystem.BusBookingSystem.Mapper.BusDetailsMapper;
import io.BookingSystem.BusBookingSystem.dto.BusDto;
import io.BookingSystem.BusBookingSystem.entity.BusDetails;
import io.BookingSystem.BusBookingSystem.exceptions.BookingException;
import io.BookingSystem.BusBookingSystem.repository.BusDetailsRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log
public class BusDetailsServiceImplementation implements BusDetailsService {

	@Autowired
	private BusDetailsRepository busDetailsRepository;

	@Autowired
	BusDetailsMapper busDetailsMapper;

	@Override
	@Transactional
	public BusDto addBusDetails(BusDto busDto) {
		BusDetails busDetails = busDetailsMapper.busDtoToBusDetails(busDto);
		BusDetails saveBus = busDetailsRepository.save(busDetails);

		return busDetailsMapper.busDetailsToBusDto(saveBus);
	}

	@Override
	public void updateBusDetails(BusDto busDto) {
		BusDetails updatedDetails = busDetailsRepository.findById(busDto.getId()).orElseThrow(
				() -> new BookingException("Bus not found with the  id: " + busDto.getId(), HttpStatus.NOT_FOUND));
		// Map the updated fields from BusDto to BusDetails entity
		busDetailsMapper.updateBusDetails(busDto, updatedDetails);
		// if the time is updated then we need yo recalculate the duration for that we
		// are recalling the duration method
		updatedDetails.calculateDuration();
		// Save the updated data in the db
		busDetailsRepository.save(updatedDetails);
		busDto.setDuration(updatedDetails.getDuration());
	}

	public BusDetails createBus(BusDetails busDetails) {
		busDetails.initializeSeats();
		return busDetailsRepository.save(busDetails);
	}
}
