package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Passenger;
import com.synergisticit.repository.PassengerRepository;

@Service
public class PassengerServiceImp implements PassengerService {
	
	@Autowired PassengerRepository passengerRepository;

	@Override
	public Passenger savePassenger(Passenger passenger) {
		return passengerRepository.save(passenger);
	}

	@Override
	public List<Passenger> findAll() {
		return passengerRepository.findAll();
	}

	@Override
	public Passenger findById(Long passengerId) {
		return passengerRepository.findById(passengerId).orElse(null);
	}

	@Override
	public void deleteById(Long passengerId) {
		passengerRepository.deleteById(passengerId);

	}

	@Override
	public Page<Passenger> findAll(Pageable pageable) {
		return passengerRepository.findAll(pageable);
	}

}
