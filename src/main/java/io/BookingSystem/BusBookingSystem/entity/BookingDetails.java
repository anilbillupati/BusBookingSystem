package io.BookingSystem.BusBookingSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "booking_details")
public class BookingDetails {

	// bus id , user id , payment id ,selected seat numbers
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private int bookingId;

	@ManyToOne
	@JoinColumn(name = "bus_id")
	private BusDetails busDetails;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ElementCollection
	private List<Integer> selectedSeats;

	@OneToOne
	@JoinColumn(name = "payment_id")
	private PaymentDetails paymentDetails;

	private LocalDateTime bookingDateTime;

}
