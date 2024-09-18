package io.BookingSystem.BusBookingSystem.Mapper;

import io.BookingSystem.BusBookingSystem.dto.BusDto;
import io.BookingSystem.BusBookingSystem.entity.BusDetails;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-18T15:15:32+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240215-1558, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class BusDetailsMapperImpl implements BusDetailsMapper {

    @Override
    public BusDto busDetailsToBusDto(BusDetails busDetails) {
        if ( busDetails == null ) {
            return null;
        }

        BusDto busDto = new BusDto();

        busDto.setId( busDetails.getBusId() );
        busDto.setArrivalCity( busDetails.getArrivalCity() );
        busDto.setArrivalTime( busDetails.getArrivalTime() );
        busDto.setBusOperator( busDetails.getBusOperator() );
        busDto.setBusType( busDetails.getBusType() );
        busDto.setDepartureCity( busDetails.getDepartureCity() );
        busDto.setDepartureDate( busDetails.getDepartureDate() );
        busDto.setDepartureTime( busDetails.getDepartureTime() );
        if ( busDetails.getDuration() != null ) {
            busDto.setDuration( busDetails.getDuration() );
        }
        busDto.setFare( busDetails.getFare() );
        busDto.setTotalSeats( busDetails.getTotalSeats() );

        return busDto;
    }

    @Override
    public BusDetails busDtoToBusDetails(BusDto busDto) {
        if ( busDto == null ) {
            return null;
        }

        BusDetails busDetails = new BusDetails();

        busDetails.setArrivalCity( busDto.getArrivalCity() );
        busDetails.setArrivalTime( busDto.getArrivalTime() );
        busDetails.setBusOperator( busDto.getBusOperator() );
        busDetails.setBusType( busDto.getBusType() );
        busDetails.setDepartureCity( busDto.getDepartureCity() );
        busDetails.setDepartureDate( busDto.getDepartureDate() );
        busDetails.setDepartureTime( busDto.getDepartureTime() );
        busDetails.setDuration( busDto.getDuration() );
        if ( busDto.getFare() != null ) {
            busDetails.setFare( busDto.getFare() );
        }
        if ( busDto.getTotalSeats() != null ) {
            busDetails.setTotalSeats( busDto.getTotalSeats() );
        }

        return busDetails;
    }

    @Override
    public void updateBusDetails(BusDto busDto, BusDetails busDetails) {
        if ( busDto == null ) {
            return;
        }

        busDetails.setArrivalCity( busDto.getArrivalCity() );
        busDetails.setArrivalTime( busDto.getArrivalTime() );
        busDetails.setBusOperator( busDto.getBusOperator() );
        busDetails.setBusType( busDto.getBusType() );
        busDetails.setDepartureCity( busDto.getDepartureCity() );
        busDetails.setDepartureDate( busDto.getDepartureDate() );
        busDetails.setDepartureTime( busDto.getDepartureTime() );
        busDetails.setDuration( busDto.getDuration() );
        if ( busDto.getFare() != null ) {
            busDetails.setFare( busDto.getFare() );
        }
        if ( busDto.getTotalSeats() != null ) {
            busDetails.setTotalSeats( busDto.getTotalSeats() );
        }
    }
}
