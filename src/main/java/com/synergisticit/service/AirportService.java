package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.Airport;

public interface AirportService {

	public Airport saveAirport(Airport airport); 
	
	public List<Airport> findAll();
	
	public Airport findById(Long airportId);
	
	public void deleteById(Long airportId);
}
