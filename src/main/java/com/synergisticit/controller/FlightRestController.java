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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.domain.Flight;
import com.synergisticit.service.FlightService;
import com.synergisticit.validation.FlightValidator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("flights")
@Secured({"DBA", "Admin"})
public class FlightRestController {

	@Autowired FlightService flightService;
	@Autowired FlightValidator flightValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(flightValidator);
	}
	
	@GetMapping(value = "findAll")
	public ResponseEntity<List<Flight>> findAll() {
		System.out.println("FlightRestController.findAll()...");
		List<Flight> flights = flightService.findAll();
		if (flights.isEmpty()) {
			return new ResponseEntity<List<Flight>>(flights, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Flight>>(flights, HttpStatus.FOUND);
		}
	} 
	
	@GetMapping(value = "findById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Flight> findById(@RequestParam Long flightId) {
		System.out.println("FlightRestController.findById()...");
		Flight flight = flightService.findById(flightId);
		if (flight == null) {
			return new ResponseEntity<Flight>(flight, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Flight>(flight, HttpStatus.FOUND);
		}
	} 
	
	@PostMapping(value = "save", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveFlight(@Valid @RequestBody Flight flight, BindingResult br) {
		System.out.println("FlightRestController.saveFlight()...");
		HttpHeaders headers = new HttpHeaders();
		StringBuilder errors = new StringBuilder("");
		Flight foundFlight = flightService.findById(flight.getFlightId());	
		
		if (foundFlight != null || br.hasFieldErrors()) {
			if (br.hasFieldErrors()) {
				List<FieldError> fieldErrors = br.getFieldErrors();
				for (FieldError fieldError : fieldErrors) {
					System.out.println("errors: " + fieldError.getDefaultMessage());
					errors = errors.append("\"" + fieldError.getField() + "\":" + fieldError.getDefaultMessage());
				}
				headers.add("errors", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.BAD_REQUEST);
			} else {
				errors.append("Flight with id: " + flight.getFlightId() + " already exists.");
				headers.add("Existing flight", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.BAD_REQUEST);
			}
		} else {
			Flight newFlight = flightService.saveFlight(flight);
			headers.add("New flight", flight.getFlightNumber());
			return new ResponseEntity<Flight>(newFlight, headers, HttpStatus.CREATED);
		}
	}
	
	@PutMapping(value = "update", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateFlight(@Valid @RequestBody Flight flight, BindingResult br) {
		System.out.println("AirportRestController.updateAirport()...");
		HttpHeaders headers = new HttpHeaders();
		StringBuilder errors = new StringBuilder("");
		Flight foundFlight = flightService.findById(flight.getFlightId());
		
		if (foundFlight == null || br.hasFieldErrors()) {
			if (br.hasFieldErrors()) {
				List<FieldError> fieldErrors = br.getFieldErrors();
				for (FieldError fieldError : fieldErrors) {
					System.out.println("errors: " + fieldError.getDefaultMessage());
					errors = errors.append("\"" + fieldError.getField() + "\":" + fieldError.getDefaultMessage());
				}
				headers.add("errors", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.BAD_REQUEST);
			} else {
				errors.append("No flight with id: " + flight.getFlightId());
				headers.add("No flight exists", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.NOT_FOUND);
			}
		} else {
			flightService.saveFlight(flight);
			return new ResponseEntity<Flight>(flight, HttpStatus.OK);
		}
	}
	
	@DeleteMapping(value = "delete")
	public ResponseEntity<Flight> delete(@RequestParam Long flightId) {
		System.out.println("AirportRestController.delete()...");
		HttpHeaders headers = new HttpHeaders();
		Flight flight = flightService.findById(flightId);
		
		if (flight == null) {
			return new ResponseEntity<Flight>(HttpStatus.NOT_FOUND);
		} else {
			flightService.deleteById(flightId);
			headers.add("Flight deleted", String.valueOf(flightId));
			return new ResponseEntity<Flight>(flight, headers, HttpStatus.ACCEPTED);
		}
	}
}
