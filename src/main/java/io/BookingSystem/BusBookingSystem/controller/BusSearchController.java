package io.BookingSystem.BusBookingSystem.controller;

import io.BookingSystem.BusBookingSystem.dto.BusDto;
import io.BookingSystem.BusBookingSystem.dto.BusSearchDto;
import io.BookingSystem.BusBookingSystem.dto.BusSearchResponseDto;
import io.BookingSystem.BusBookingSystem.dto.SeatsDto;
import io.BookingSystem.BusBookingSystem.service.BusSearchService;
import io.BookingSystem.BusBookingSystem.service.SeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BusSearchController {

	// search the bus based n the details mentioned
	@Autowired
	private BusSearchService busSearchService;
	@Autowired
	private SeatsService seatsService;

	@PreAuthorize("hasAuthority('User')")
	@PostMapping("/searchBus")
	public ResponseEntity<List<BusSearchResponseDto>> searchBuses(@RequestBody BusSearchDto busSearchDto) {
		List<BusDto> buses = busSearchService.searchBuses(busSearchDto);
		buses = busSearchService.sortAndFilterBuses(buses, busSearchDto.getSortBy(), busSearchDto.getBusType(),
				busSearchDto.getBusOperator());
		List<BusSearchResponseDto> response = new ArrayList<>();
		buses.parallelStream().forEach(bus -> {
			response.add(new BusSearchResponseDto(bus,
					seatsService.availableSeats(bus.getId()).stream().map(SeatsDto::getSeatId).toList()));
		});
		return ResponseEntity.ok(response);
	}

}
