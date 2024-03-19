package com.synergisticit.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synergisticit.domain.Passenger;

public interface PassengerService {

	public Passenger savePassenger(Passenger passenger); 
	
	public List<Passenger> findAll();
	
	public Passenger findById(Long passengerId);
	
	public void deleteById(Long passengerId);
	
	public Page<Passenger> findAll(Pageable pageable);
}
