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

import com.synergisticit.domain.Airlines;
import com.synergisticit.service.AirlinesService;
import com.synergisticit.validation.AirlinesValidator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("airlines")
@Secured({"DBA", "Admin"})
public class AirlineRestController {

	@Autowired AirlinesService airlinesService;
	@Autowired AirlinesValidator airlinesValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(airlinesValidator);
	}
	
	@GetMapping(value = "findAll")
	public ResponseEntity<List<Airlines>> findAll() {
		System.out.println("AirlineRestController.findAll()...");
		List<Airlines> airlines = airlinesService.findAll();
		if (airlines.isEmpty()) {
			return new ResponseEntity<List<Airlines>>(airlines, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Airlines>>(airlines, HttpStatus.FOUND);
		}
	} 
	
	@GetMapping(value = "findById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Airlines> findById(@RequestParam Long airlinesId) {
		System.out.println("AirlineRestController.findById()...");
		Airlines airline = airlinesService.findById(airlinesId);
		if (airline == null) {
			return new ResponseEntity<Airlines>(airline, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Airlines>(airline, HttpStatus.FOUND);
		}
	} 
	
	@PostMapping(value = "save", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveAirline(@Valid @RequestBody Airlines airline, BindingResult br) {
		System.out.println("AirlineRestController.saveAirline()...");
		HttpHeaders headers = new HttpHeaders();
		StringBuilder errors = new StringBuilder("");
		Airlines foundAirline = airlinesService.findById(airline.getAirlinesId());	
		
		if (foundAirline != null || br.hasFieldErrors()) {
			if (br.hasFieldErrors()) {
				List<FieldError> fieldErrors = br.getFieldErrors();
				for (FieldError fieldError : fieldErrors) {
					System.out.println("errors: " + fieldError.getDefaultMessage());
					errors = errors.append("\"" + fieldError.getField() + "\":" + fieldError.getDefaultMessage());
				}
				headers.add("errors", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.BAD_REQUEST);
			} else {
				errors.append("Airline with id: " + airline.getAirlinesId() + " already exists.");
				headers.add("Existing airline", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.BAD_REQUEST);
			}
		} else {
			Airlines newAirline = airlinesService.saveAirlines(airline);
			headers.add("New airline", airline.getAirlinesName());
			return new ResponseEntity<Airlines>(newAirline, headers, HttpStatus.CREATED);
		}
	}
	
	@PutMapping(value = "update", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateAirline(@Valid @RequestBody Airlines airline, BindingResult br) {
		System.out.println("AirlineRestController.updateAirline()...");
		HttpHeaders headers = new HttpHeaders();
		StringBuilder errors = new StringBuilder("");
		Airlines foundAirline = airlinesService.findById(airline.getAirlinesId());
		
		if (foundAirline == null || br.hasFieldErrors()) {
			if (br.hasFieldErrors()) {
				List<FieldError> fieldErrors = br.getFieldErrors();
				for (FieldError fieldError : fieldErrors) {
					System.out.println("errors: " + fieldError.getDefaultMessage());
					errors = errors.append("\"" + fieldError.getField() + "\":" + fieldError.getDefaultMessage());
				}
				headers.add("errors", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.BAD_REQUEST);
			} else {
				errors.append("No airline with id: " + airline.getAirlinesId());
				headers.add("No airline exists", errors.toString());
				return new ResponseEntity<StringBuilder>(errors, headers, HttpStatus.NOT_FOUND);
			}
		} else {
			airlinesService.saveAirlines(airline);
			return new ResponseEntity<Airlines>(airline, HttpStatus.OK);
		}
	}
	
	@DeleteMapping(value = "delete")
	public ResponseEntity<Airlines> delete(@RequestParam Long airlinesId) {
		System.out.println("AirlineRestController.delete()...");
		HttpHeaders headers = new HttpHeaders();
		Airlines airline = airlinesService.findById(airlinesId);
		
		if (airline == null) {
			return new ResponseEntity<Airlines>(HttpStatus.NOT_FOUND);
		} else {
			airlinesService.deleteById(airlinesId);
			headers.add("Airline deleted", String.valueOf(airlinesId));
			return new ResponseEntity<Airlines>(airline, headers, HttpStatus.ACCEPTED);
		}
	}
}
