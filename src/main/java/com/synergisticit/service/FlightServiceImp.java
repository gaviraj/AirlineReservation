package com.synergisticit.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Flight;
import com.synergisticit.repository.FlightRepository;

@Service
public class FlightServiceImp implements FlightService {

	@Autowired FlightRepository flightRepository;
	
	@Override
	public Flight saveFlight(Flight flight) {
		return flightRepository.save(flight);
	}

	@Override
	public List<Flight> findAll() {
		return flightRepository.findAll();
	}

	@Override
	public Flight findById(Long flightId) {
		return flightRepository.findById(flightId).orElse(null);
	}

	@Override
	public void deleteById(Long flightId) {
		flightRepository.deleteById(flightId);

	}

	@Override
	public List<Flight> searchFlights(String departureCity, String arrivalCity, LocalDate departureDate, LocalDate arrivalDate) {
		return flightRepository.findByDepartureCityContainingIgnoreCaseAndArrivalCityContainingIgnoreCaseAndDepartureDateEqualsAndArrivalDateEquals(departureCity, arrivalCity, departureDate, arrivalDate);
	}

}
