package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.Reservation;

public interface ReservationService {

	public Reservation saveReservation(Reservation reservation); 
	
	public List<Reservation> findAll();
	
	public Reservation findById(Long reservationNumber);
	
	public void deleteById(Long reservationNumber);
	
	public List<Reservation> findByPassengerEmail(String email);
}
