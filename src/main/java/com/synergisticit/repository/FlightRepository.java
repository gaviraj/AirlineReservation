package com.synergisticit.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.synergisticit.domain.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {

	List<Flight> findByDepartureCityContainingIgnoreCaseAndArrivalCityContainingIgnoreCaseAndDepartureDateEqualsAndArrivalDateEquals(
			String departureCity, String arrivalCity, LocalDate departureDate, LocalDate arrivalDate
	);
	
	Page<Flight> findAll(Pageable pageable);
}
