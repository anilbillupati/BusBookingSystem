package io.BookingSystem.BusBookingSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seats")
public class Seats {

	// this field is having the seats that are equal to the total seats field in the
	// bus details
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seat_id")
	private int seatId;

	// joined using the bus id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bus_id")
	@JsonBackReference
	private BusDetails busDetails;

	// a seat is available when it is true if booked then it is false
	// when ever someone booked the seat for respective seat should be flagged to
	// false
	@Column(name = "is_available")
	private boolean isAvailable = true;

}
