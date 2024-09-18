package io.BookingSystem.BusBookingSystem.Mapper;

import io.BookingSystem.BusBookingSystem.dto.BookingDto;
import io.BookingSystem.BusBookingSystem.entity.BookingDetails;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-18T15:15:32+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240215-1558, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class BookingMapperImpl implements BookingMapper {

    @Override
    public BookingDto bookingDetailsToBookingDto(BookingDetails bookingDetails) {
        if ( bookingDetails == null ) {
            return null;
        }

        BookingDto bookingDto = new BookingDto();

        bookingDto.setBookingDateTime( bookingDetails.getBookingDateTime() );
        bookingDto.setBookingId( bookingDetails.getBookingId() );
        List<Integer> list = bookingDetails.getSelectedSeats();
        if ( list != null ) {
            bookingDto.setSelectedSeats( new ArrayList<Integer>( list ) );
        }

        return bookingDto;
    }

    @Override
    public BookingDetails bookingDtoToBookingDetails(BookingDto bookingDto) {
        if ( bookingDto == null ) {
            return null;
        }

        BookingDetails bookingDetails = new BookingDetails();

        bookingDetails.setBookingDateTime( bookingDto.getBookingDateTime() );
        bookingDetails.setBookingId( bookingDto.getBookingId() );
        List<Integer> list = bookingDto.getSelectedSeats();
        if ( list != null ) {
            bookingDetails.setSelectedSeats( new ArrayList<Integer>( list ) );
        }

        return bookingDetails;
    }
}
