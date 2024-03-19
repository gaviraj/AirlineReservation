package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Airport;
import com.synergisticit.repository.AirportRepository;

@Service
public class AirportServiceImp implements AirportService {

	@Autowired AirportRepository airportRepository;
	
	@Override
	public Airport saveAirport(Airport airport) {
		return airportRepository.save(airport);
	}

	@Override
	public List<Airport> findAll() {
		return airportRepository.findAll();
	}

	@Override
	public Airport findById(Long airportId) {
		return airportRepository.findById(airportId).orElse(null);
	}

	@Override
	public void deleteById(Long airportId) {
		airportRepository.deleteById(airportId);

	}

	@Override
	public Page<Airport> findAll(Pageable pageable) {
		return airportRepository.findAll(pageable);
	}

}
