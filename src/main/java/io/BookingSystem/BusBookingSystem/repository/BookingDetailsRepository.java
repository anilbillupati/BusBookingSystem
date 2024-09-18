package io.BookingSystem.BusBookingSystem.repository;

import io.BookingSystem.BusBookingSystem.entity.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Integer> {

}
