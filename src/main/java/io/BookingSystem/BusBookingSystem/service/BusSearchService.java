package io.BookingSystem.BusBookingSystem.service;

import io.BookingSystem.BusBookingSystem.dto.BusDto;
import io.BookingSystem.BusBookingSystem.dto.BusSearchDto;

import java.util.List;

public interface BusSearchService {

    List<BusDto> searchBuses(BusSearchDto busSearchDto);

    List<BusDto> sortAndFilterBuses(List<BusDto> buses, String sortBy, String busType, String busOperator);
}
