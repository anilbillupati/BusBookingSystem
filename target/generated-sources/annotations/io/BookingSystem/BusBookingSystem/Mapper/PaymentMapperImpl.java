package io.BookingSystem.BusBookingSystem.Mapper;

import io.BookingSystem.BusBookingSystem.dto.PaymentDto;
import io.BookingSystem.BusBookingSystem.entity.BusDetails;
import io.BookingSystem.BusBookingSystem.entity.PaymentDetails;
import io.BookingSystem.BusBookingSystem.entity.User;
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
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentDto paymentDetailsToPaymentDto(PaymentDetails paymentDetails) {
        if ( paymentDetails == null ) {
            return null;
        }

        PaymentDto paymentDto = new PaymentDto();

        paymentDto.setBusId( paymentDetailsBusDetailsBusId( paymentDetails ) );
        paymentDto.setUserId( paymentDetailsUserId( paymentDetails ) );
        paymentDto.setAmount( paymentDetails.getAmount() );
        paymentDto.setPaymentDateTime( paymentDetails.getPaymentDateTime() );
        paymentDto.setPaymentId( paymentDetails.getPaymentId() );
        paymentDto.setPaymentStatus( paymentDetails.isPaymentStatus() );
        paymentDto.setPaymentType( paymentDetails.getPaymentType() );
        List<Integer> list = paymentDetails.getSelectedSeats();
        if ( list != null ) {
            paymentDto.setSelectedSeats( new ArrayList<Integer>( list ) );
        }

        return paymentDto;
    }

    @Override
    public PaymentDetails paymentDtoToPaymentDetails(PaymentDto paymentDto) {
        if ( paymentDto == null ) {
            return null;
        }

        PaymentDetails paymentDetails = new PaymentDetails();

        paymentDetails.setBusDetails( paymentDtoToBusDetails( paymentDto ) );
        paymentDetails.setUser( paymentDtoToUser( paymentDto ) );
        paymentDetails.setAmount( paymentDto.getAmount() );
        paymentDetails.setPaymentDateTime( paymentDto.getPaymentDateTime() );
        paymentDetails.setPaymentId( paymentDto.getPaymentId() );
        paymentDetails.setPaymentStatus( paymentDto.isPaymentStatus() );
        paymentDetails.setPaymentType( paymentDto.getPaymentType() );
        List<Integer> list = paymentDto.getSelectedSeats();
        if ( list != null ) {
            paymentDetails.setSelectedSeats( new ArrayList<Integer>( list ) );
        }

        return paymentDetails;
    }

    private int paymentDetailsBusDetailsBusId(PaymentDetails paymentDetails) {
        if ( paymentDetails == null ) {
            return 0;
        }
        BusDetails busDetails = paymentDetails.getBusDetails();
        if ( busDetails == null ) {
            return 0;
        }
        int busId = busDetails.getBusId();
        return busId;
    }

    private int paymentDetailsUserId(PaymentDetails paymentDetails) {
        if ( paymentDetails == null ) {
            return 0;
        }
        User user = paymentDetails.getUser();
        if ( user == null ) {
            return 0;
        }
        int id = user.getId();
        return id;
    }

    protected BusDetails paymentDtoToBusDetails(PaymentDto paymentDto) {
        if ( paymentDto == null ) {
            return null;
        }

        BusDetails busDetails = new BusDetails();

        busDetails.setBusId( paymentDto.getBusId() );

        return busDetails;
    }

    protected User paymentDtoToUser(PaymentDto paymentDto) {
        if ( paymentDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( paymentDto.getUserId() );

        return user;
    }
}
