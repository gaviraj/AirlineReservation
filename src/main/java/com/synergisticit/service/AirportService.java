package com.synergisticit.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synergisticit.domain.Airport;

public interface AirportService {

	public Airport saveAirport(Airport airport); 
	
	public List<Airport> findAll();
	
	public Airport findById(Long airportId);
	
	public void deleteById(Long airportId);
	
	public Page<Airport> findAll(Pageable pageable);
}
