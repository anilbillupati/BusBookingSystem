package io.BookingSystem.BusBookingSystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "payment_details")
public class PaymentDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private int paymentId;
	@ManyToOne
	@JoinColumn(name = "bus_id")
	private BusDetails busDetails;
	@ManyToOne
	@JoinColumn(name = "id")
	private User user;
	@Column(name = "selected_seats")
	private List<Integer> selectedSeats;
	@Column(name = "amount")
	private int amount;
	private String paymentType;
	@Column(name = "payment_date")
	private LocalDateTime paymentDateTime;
	@Column(name = "payment_status")
	private boolean paymentStatus;
}
