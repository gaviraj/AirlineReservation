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

import com.synergisticit.domain.Passenger;
import com.synergisticit.service.PassengerService;
import com.synergisticit.validation.PassengerValidator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("passengers")
@Secured({"DBA", "Admin"})
public class PassengerRestController {

	@Autowired PassengerService passengerService;
	@Autowired PassengerValidator passengerValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(passengerValidator);
	}
	
	@GetMapping(value = "findAll")
	public ResponseEntity<List<Passenger>> findAll() {
		System.out.println("PassengerRestController.findAll()...");
		List<Passenger> passengers = passengerService.findAll();
		if (passengers.isEmpty()) {
			return new ResponseEntity<List<Passenger>>(passengers, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Passenger>>(passengers, HttpStatus.FOUND);
		}
	} 
	
	@GetMapping(value = "findById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Passenger> findById(@RequestParam Long passengerId) {
		System.out.println("PassengerRestController.findById()...");
		Passenger passenger = passengerService.findById(passengerId);
		if (passenger == null) {
			return new ResponseEntity<Passenger>(passenger, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Passenger>(passenger, HttpStatus.FOUND);
		}
	} 
	
	@PostMapping(value = "save", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> savePassenger(@Valid @RequestBody Passenger passenger, BindingResult br) {
		System.out.println("PassengerRestController.savePassenger()...");
		HttpHeaders headers = new HttpHeaders();
		StringBuilder errors = new StringBuilder("");
		Passenger foundPassenger = passengerService.findById(passenger.getPassengerId());	
		
		if (foundPassenger != null || br.hasFieldErrors()) {
			if (br.hasFieldErrors()) {
				List<FieldError> fieldErrors = br.getFieldErrors();
				for (FieldError fieldError : fieldErrors) {
					System.out.println("errors: " + fieldError.getDefaultMessage());
					errors = errors.append("\"" + fieldError.getField() + "\":" + fieldError.getDefaultMessage());
				}
				headers.add("errors", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.BAD_REQUEST);
			} else {
				errors.append("Passenger with id: " + passenger.getPassengerId() + " already exists.");
				headers.add("Existing passenger", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.BAD_REQUEST);
			}
		} else {
			Passenger newPassenger = passengerService.savePassenger(passenger);
			headers.add("New passenger", passenger.getFirstName() + " " + passenger.getLastName());
			return new ResponseEntity<Passenger>(newPassenger, headers, HttpStatus.CREATED);
		}
	}
	
	@PutMapping(value = "update", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updatePassenger(@Valid @RequestBody Passenger passenger, BindingResult br) {
		System.out.println("PassengerRestController.updatePassenger()...");
		HttpHeaders headers = new HttpHeaders();
		StringBuilder errors = new StringBuilder("");
		Passenger foundPassenger = passengerService.findById(passenger.getPassengerId());
		
		if (foundPassenger == null || br.hasFieldErrors()) {
			if (br.hasFieldErrors()) {
				List<FieldError> fieldErrors = br.getFieldErrors();
				for (FieldError fieldError : fieldErrors) {
					System.out.println("errors: " + fieldError.getDefaultMessage());
					errors = errors.append("\"" + fieldError.getField() + "\":" + fieldError.getDefaultMessage());
				}
				headers.add("errors", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.BAD_REQUEST);
			} else {
				errors.append("No passenger with id: " + passenger.getPassengerId());
				headers.add("No passenger exists", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.NOT_FOUND);
			}
		} else {
			passengerService.savePassenger(passenger);
			return new ResponseEntity<Passenger>(passenger, HttpStatus.OK);
		}
	}
	
	@DeleteMapping(value = "delete")
	public ResponseEntity<Passenger> delete(@RequestParam Long passengerId) {
		System.out.println("AirportRestController.delete()...");
		HttpHeaders headers = new HttpHeaders();
		Passenger passenger = passengerService.findById(passengerId);
		
		if (passenger == null) {
			return new ResponseEntity<Passenger>(HttpStatus.NOT_FOUND);
		} else {
			passengerService.deleteById(passengerId);
			headers.add("Flight deleted", String.valueOf(passengerId));
			return new ResponseEntity<Passenger>(passenger, headers, HttpStatus.ACCEPTED);
		}
	}
	
}
