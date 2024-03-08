package com.synergisticit.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synergisticit.domain.Flight;

public interface FlightService {

	public Flight saveFlight(Flight flight); 
	
	public List<Flight> findAll();
	
	public Flight findById(Long flightId);
	
	public void deleteById(Long flightId);
	
	public List<Flight> searchFlights(
			String departureCity, String arrivalCity, LocalDate departureDate, LocalDate arrivalDate
	);
	
	public Page<Flight> findAll(Pageable page);
}
