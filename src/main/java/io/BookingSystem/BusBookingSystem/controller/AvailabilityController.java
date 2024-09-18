package io.BookingSystem.BusBookingSystem.controller;

import io.BookingSystem.BusBookingSystem.dto.SeatsDto;
import io.BookingSystem.BusBookingSystem.service.SeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AvailabilityController {

    @Autowired
    private SeatsService seatsService;

    @PreAuthorize("hasAuthority('User')")
    @GetMapping("/availableSeats/{busId}")
    public ResponseEntity<List<SeatsDto>> getAvailableSeats(@PathVariable int busId) {
        List<SeatsDto> availableSeatsList = seatsService.availableSeats(busId);
        return ResponseEntity.ok(availableSeatsList);
    }

    @PreAuthorize("hasAuthority('User')")
    @GetMapping("/bookedSeats/{busId}")
    public ResponseEntity<List<SeatsDto>> getBookedSeats(@PathVariable int busId) {
        List<SeatsDto> bookedSeatsList = seatsService.bookedSeats(busId);
        return ResponseEntity.ok(bookedSeatsList);
    }
}
