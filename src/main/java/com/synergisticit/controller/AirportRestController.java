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

import com.synergisticit.domain.Airport;
import com.synergisticit.service.AirportService;
import com.synergisticit.validation.AirportValidator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("airports")
@Secured({"DBA", "Admin"})
public class AirportRestController {

	@Autowired AirportService airportService;
	@Autowired AirportValidator airportValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(airportValidator);
	}
	
	@GetMapping(value = "findAll")
	public ResponseEntity<List<Airport>> findAll() {
		System.out.println("AirportRestController.findAll()...");
		List<Airport> airports = airportService.findAll();
		if (airports.isEmpty()) {
			return new ResponseEntity<List<Airport>>(airports, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Airport>>(airports, HttpStatus.FOUND);
		}
	} 
	
	@GetMapping(value = "findById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Airport> findById(@RequestParam Long airportId) {
		System.out.println("AirportRestController.findById()...");
		Airport airport = airportService.findById(airportId);
		if (airport == null) {
			return new ResponseEntity<Airport>(airport, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Airport>(airport, HttpStatus.FOUND);
		}
	} 
	
	@PostMapping(value = "save", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveAirport(@Valid @RequestBody Airport airport, BindingResult br) {
		System.out.println("AirportRestController.saveAirport()...");
		HttpHeaders headers = new HttpHeaders();
		StringBuilder errors = new StringBuilder("");
		Airport foundAirport = airportService.findById(airport.getAirportId());	
		
		if (foundAirport != null || br.hasFieldErrors()) {
			if (br.hasFieldErrors()) {
				List<FieldError> fieldErrors = br.getFieldErrors();
				for (FieldError fieldError : fieldErrors) {
					System.out.println("errors: " + fieldError.getDefaultMessage());
					errors = errors.append("\"" + fieldError.getField() + "\":" + fieldError.getDefaultMessage());
				}
				headers.add("errors", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.BAD_REQUEST);
			} else {
				errors.append("Airport with id: " + airport.getAirportId() + " already exists.");
				headers.add("Existing airport", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.BAD_REQUEST);
			}
		} else {
			Airport newAirport = airportService.saveAirport(airport);
			headers.add("New airline", airport.getAirportName());
			return new ResponseEntity<Airport>(newAirport, headers, HttpStatus.CREATED);
		}
	}
	
	@PutMapping(value = "update", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateAirport(@Valid @RequestBody Airport airport, BindingResult br) {
		System.out.println("AirportRestController.updateAirport()...");
		HttpHeaders headers = new HttpHeaders();
		StringBuilder errors = new StringBuilder("");
		Airport foundAirport = airportService.findById(airport.getAirportId());	
		
		if (foundAirport == null || br.hasFieldErrors()) {
			if (br.hasFieldErrors()) {
				List<FieldError> fieldErrors = br.getFieldErrors();
				for (FieldError fieldError : fieldErrors) {
					System.out.println("errors: " + fieldError.getDefaultMessage());
					errors = errors.append("\"" + fieldError.getField() + "\":" + fieldError.getDefaultMessage());
				}
				headers.add("errors", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.BAD_REQUEST);
			} else {
				errors.append("No airport with id: " + airport.getAirportId());
				headers.add("No airport exists", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.NOT_FOUND);
			}
		} else {
			airportService.saveAirport(airport);
			return new ResponseEntity<Airport>(airport, HttpStatus.OK);
		}
	}
	
	@DeleteMapping(value = "delete")
	public ResponseEntity<Airport> delete(@RequestParam Long airportId) {
		System.out.println("AirportRestController.delete()...");
		HttpHeaders headers = new HttpHeaders();
		Airport airport = airportService.findById(airportId);
		
		if (airport == null) {
			return new ResponseEntity<Airport>(HttpStatus.NOT_FOUND);
		} else {
			airportService.deleteById(airportId);
			headers.add("Airport deleted", String.valueOf(airportId));
			return new ResponseEntity<Airport>(airport, headers, HttpStatus.ACCEPTED);
		}
	}
}
