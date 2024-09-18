package io.BookingSystem.BusBookingSystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "bus_details")
public class BusDetails {
	// define the fields -> annotate each one -> if there is mappings and then
	// include it
	// generate getters and setters //no arg and all arg constructor
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bus_id")
	private int busId;
	@Column(name = "departure_city")
	private String departureCity;
	@Column(name = "arrival_city")
	private String arrivalCity;
	@Column(name = "bus_operator")
	private String busOperator;
	@Column(name = "bus_type")
	private String busType;
	@Column(name = "departure_date")
	private LocalDate departureDate;
	@Column(name = "departure_time")
	private LocalTime departureTime;
	@Column(name = "arrival_time")
	private LocalTime arrivalTime;
	@Column(name = "fare")
	private int fare;
	@Column(name = "total_seats")
	private int totalSeats;
	@Column(name = "duration")
	private double duration;

	@OneToMany(mappedBy = "busDetails", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Seats> seatNumbers = new ArrayList<>();

	@PostLoad
	@PostPersist
	@PostUpdate
	public void durationAndInitialize() {
		calculateDuration();
		initializeSeats();
	}

	public void calculateDuration() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime departure = LocalTime.parse(formatter.format(departureTime));
		LocalTime arrival = LocalTime.parse(formatter.format(arrivalTime));

		Duration duration;
		if (arrival.isBefore(departure)) {
			duration = Duration.between(departure, LocalTime.MAX).plus(Duration.between(LocalTime.MIN, arrival));
		} else {
			duration = Duration.between(departure, arrival);
		}
		this.duration = Math.round(duration.toHours() + (double) duration.toMinutesPart() / 60);

	}

	public Double getDuration() {
		if (departureTime != null && arrivalTime != null) {
			calculateDuration();
		}
		return this.duration;
	}

	public boolean canAddMoreSeats() {
		return seatNumbers.size() < totalSeats;
	}

	public void addSeat(Seats seat) {
		if (canAddMoreSeats()) {
			seatNumbers.add(seat);
			seat.setBusDetails(this);
		} else {
			throw new IllegalStateException("The bus is already full you can not add more seats.");
		}
	}

	public void initializeSeats() {
		for (int i = 0; i < this.totalSeats; i++) {
			Seats seat = new Seats();
			seat.setBusDetails(this);
			seat.setAvailable(true); // Set all seats as unavailable initially bcz upon starting no seat is booked
			this.seatNumbers.add(seat);
		}
	}
}