package io.BookingSystem.BusBookingSystem.Mapper;

import io.BookingSystem.BusBookingSystem.dto.SeatsDto;
import io.BookingSystem.BusBookingSystem.entity.BusDetails;
import io.BookingSystem.BusBookingSystem.entity.Seats;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-18T15:15:33+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240215-1558, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class SeatsMapperImpl implements SeatsMapper {

    @Override
    public SeatsDto seatsToSeatsDto(Seats seats) {
        if ( seats == null ) {
            return null;
        }

        SeatsDto seatsDto = new SeatsDto();

        seatsDto.setBusId( seatsBusDetailsBusId( seats ) );
        seatsDto.setAvailable( seats.isAvailable() );
        seatsDto.setSeatId( seats.getSeatId() );

        return seatsDto;
    }

    @Override
    public Seats seatsDtoToSeats(SeatsDto seatsDto) {
        if ( seatsDto == null ) {
            return null;
        }

        Seats seats = new Seats();

        seats.setBusDetails( seatsDtoToBusDetails( seatsDto ) );
        seats.setAvailable( seatsDto.isAvailable() );
        seats.setSeatId( seatsDto.getSeatId() );

        return seats;
    }

    @Override
    public List<SeatsDto> seatsToSeatsDtoList(List<Seats> seats) {
        if ( seats == null ) {
            return null;
        }

        List<SeatsDto> list = new ArrayList<SeatsDto>( seats.size() );
        for ( Seats seats1 : seats ) {
            list.add( seatsToSeatsDto( seats1 ) );
        }

        return list;
    }

    private int seatsBusDetailsBusId(Seats seats) {
        if ( seats == null ) {
            return 0;
        }
        BusDetails busDetails = seats.getBusDetails();
        if ( busDetails == null ) {
            return 0;
        }
        int busId = busDetails.getBusId();
        return busId;
    }

    protected BusDetails seatsDtoToBusDetails(SeatsDto seatsDto) {
        if ( seatsDto == null ) {
            return null;
        }

        BusDetails busDetails = new BusDetails();

        busDetails.setBusId( seatsDto.getBusId() );

        return busDetails;
    }
}
