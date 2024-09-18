package io.BookingSystem.BusBookingSystem.repository;

import io.BookingSystem.BusBookingSystem.entity.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatsRepository extends JpaRepository<Seats, Integer> {
	@Query(value = "SELECT * FROM seats WHERE bus_id = :busId AND is_available = true", nativeQuery = true)
	List<Seats> findAvailableSeatsByBusId(@Param("busId") int busId);

	@Query(value = "SELECT * FROM seats WHERE bus_id = :busId AND is_available = false", nativeQuery = true)
	List<Seats> findBookedSeatsByBusId(@Param("busId") int busId);

}
