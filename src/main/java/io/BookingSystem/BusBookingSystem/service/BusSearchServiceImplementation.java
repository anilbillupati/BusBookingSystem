package io.BookingSystem.BusBookingSystem.service;

import io.BookingSystem.BusBookingSystem.Mapper.BusDetailsMapper;
import io.BookingSystem.BusBookingSystem.dto.BusDto;
import io.BookingSystem.BusBookingSystem.dto.BusSearchDto;

import io.BookingSystem.BusBookingSystem.entity.BusDetails;
import io.BookingSystem.BusBookingSystem.exceptions.BookingException;
import io.BookingSystem.BusBookingSystem.repository.BusDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusSearchServiceImplementation implements BusSearchService {

	@Autowired
	private BusDetailsRepository busDetailsRepository;

	@Autowired
	private BusDetailsMapper busDetailsMapper;

	// method to return the bus details
	@Override
	public List<BusDto> searchBuses(BusSearchDto busSearchDto) {
		List<BusDetails> busDetailsList = busDetailsRepository.findBySearchCriteria(busSearchDto.getDepartureCity(),
				busSearchDto.getArrivalCity(), busSearchDto.getDepartureDate(), busSearchDto.getNoOfPassengers());

		if (busDetailsList.isEmpty()) {
			throw new BookingException("No buses found for the specified route !!!.", HttpStatus.NOT_FOUND);
		}
		return busDetailsList.stream().map(busDetailsMapper::busDetailsToBusDto).collect(Collectors.toList());
	}

	// method to return the
	@Override
	public List<BusDto> sortAndFilterBuses(List<BusDto> buses, String sortBy, String busType, String busOperator) {
		// filtering based on the type of bus eg: sleeper ,ac ,non ac
		if (busType != null && !busType.isEmpty()) {
			buses = buses.stream().filter(bus -> busType.equals(bus.getBusType())).collect(Collectors.toList());
		}

		// Filter by bus operator kalyani Travels , vihari Travels ,kaveri Travels
		if (busOperator != null && !busOperator.isEmpty()) {
			buses = buses.stream().filter(bus -> busOperator.equals(bus.getBusOperator())).collect(Collectors.toList());
		}
		// soritng
		if (sortBy != null) {
			switch (sortBy.toLowerCase()) {
			case "departuretime":
				buses.sort(Comparator.comparing(BusDto::getDepartureTime));
				break;
			case "arrivaltime":
				buses.sort(Comparator.comparing(BusDto::getArrivalTime));
				break;
			case "duration":
				buses.sort(Comparator.comparing(BusDto::getDuration));
				break;
			case "fare":
				buses.sort(Comparator.comparing(BusDto::getFare));
				break;
			default:
				break;
			}
		}
		return buses;
	}
}
