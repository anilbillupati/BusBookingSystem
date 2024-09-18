package io.BookingSystem.BusBookingSystem.repository;

import io.BookingSystem.BusBookingSystem.entity.BusDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BusDetailsRepository extends JpaRepository<BusDetails, Integer> {
	@Query("SELECT b FROM BusDetails b WHERE b.departureCity = :departureCity AND b.arrivalCity = :arrivalCity AND b.departureDate = :departureDate AND b.totalSeats >= :noOfPassengers")
	List<BusDetails> findBySearchCriteria(@Param("departureCity") String departureCity,
			@Param("arrivalCity") String arrivalCity, @Param("departureDate") LocalDate departureDate,
			@Param("noOfPassengers") int noOfPassengers);

}
