package io.BookingSystem.BusBookingSystem.repository;

import io.BookingSystem.BusBookingSystem.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Integer> {

}
