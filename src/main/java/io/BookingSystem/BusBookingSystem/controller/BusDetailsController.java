package io.BookingSystem.BusBookingSystem.controller;

import io.BookingSystem.BusBookingSystem.dto.BusDto;
import io.BookingSystem.BusBookingSystem.service.BusDetailsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class BusDetailsController {

	@Autowired
	private BusDetailsService busDetailsService;

	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping("/add")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<BusDto> addingBusDetails(@Valid @RequestBody BusDto busDto) {
		BusDto bus1 = busDetailsService.addBusDetails(busDto);
		return new ResponseEntity<>(bus1, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('Admin')")
	@PutMapping("/update/{id}")
	public ResponseEntity<BusDto> updatingBusDetails(@PathVariable int id, @RequestBody BusDto busDto) {
		busDto.setId(id);
		busDetailsService.updateBusDetails(busDto);
		return ResponseEntity.ok(busDto);
	}

}
