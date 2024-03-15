package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Reservation;
import com.synergisticit.repository.ReservationRepository;

@Service
public class ReservationServiceImp implements ReservationService {

	@Autowired ReservationRepository reservationRepository;
	
	@Override
	public Reservation saveReservation(Reservation reservation) {
		return reservationRepository.save(reservation);
	}

	@Override
	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}

	@Override
	public Reservation findById(Long reservationNumber) {
		return reservationRepository.findById(reservationNumber).orElse(null);
	}

	@Override
	public void deleteById(Long reservationNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Reservation> findByPassengerEmail(String email) {
		return reservationRepository.findByPassengerEmail(email);
	}

	@Override
	public Page<Reservation> findAll(Pageable pageable) {
		return reservationRepository.findAll(pageable);
	}

}
