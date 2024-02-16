package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.domain.Flight;
import com.synergisticit.domain.Passenger;
import com.synergisticit.domain.Reservation;
import com.synergisticit.service.FlightService;
import com.synergisticit.service.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("reservations")
@Secured({"DBA", "Admin"})
public class ReservationRestController {

	@Autowired ReservationService reservationService;
	@Autowired FlightService flightService;
	
	@GetMapping(value = "findAll")
	public ResponseEntity<List<Reservation>> findAll() {
		System.out.println("ReservationRestController.findAll()...");
		List<Reservation> reservations = reservationService.findAll();
		if (reservations.isEmpty()) {
			return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.FOUND);
		}
	} 
	
	@GetMapping(value = "findById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Reservation> findById(@RequestParam Long reservationNumber) {
		System.out.println("PassengerRestController.findById()...");
		Reservation reservation = reservationService.findById(reservationNumber);
		if (reservation == null) {
			return new ResponseEntity<Reservation>(reservation, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Reservation>(reservation, HttpStatus.FOUND);
		}
	} 
	
	@PostMapping(value = "save", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveReservation(@Valid @RequestBody Reservation reservation, BindingResult br) {
		System.out.println("PassengerRestController.saveReservation()...");
		HttpHeaders headers = new HttpHeaders();
		StringBuilder errors = new StringBuilder("");
		Reservation foundReservation = reservationService.findById(reservation.getReservationNumber());	
		
		if (foundReservation != null || br.hasFieldErrors()) {
			if (br.hasFieldErrors()) {
				List<FieldError> fieldErrors = br.getFieldErrors();
				for (FieldError fieldError : fieldErrors) {
					System.out.println("errors: " + fieldError.getDefaultMessage());
					errors = errors.append("\"" + fieldError.getField() + "\":" + fieldError.getDefaultMessage());
				}
				headers.add("errors", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.BAD_REQUEST);
			} else {
				errors.append("Reservation with id: " + reservation.getReservationNumber() + " already exists.");
				headers.add("Existing reservation", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.BAD_REQUEST);
			}
		} else {
			Reservation newReservation = reservationService.saveReservation(reservation);
			Flight flight = newReservation.getFlight();
			flight.setSeatsBooked(flight.getSeatsBooked() + 1);
			flightService.saveFlight(flight);
			headers.add("New reservation", String.valueOf(reservation.getReservationNumber()));
			return new ResponseEntity<Reservation>(newReservation, headers, HttpStatus.CREATED);
		}
	}
	
	@PutMapping(value = "checkIn", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Reservation> checkIn(@RequestParam Long reservationNumber, @RequestParam int checkedBags) {
		System.out.println("PassengerRestController.checkIn()...");
		Reservation reservation = reservationService.findById(reservationNumber);
		if (reservation == null) {
			return new ResponseEntity<Reservation>(reservation, HttpStatus.NOT_FOUND);
		} else {
			reservation.setCheckedBags(checkedBags);
			reservation.setCheckedIn(true);
			reservationService.saveReservation(reservation);
			return new ResponseEntity<Reservation>(reservation, HttpStatus.FOUND);
		}
	} 
}
