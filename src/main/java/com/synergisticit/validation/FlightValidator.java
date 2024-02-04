package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Flight;

@Component
public class FlightValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Flight.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Flight flight = (Flight)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "flightNumber", "flight.flightNumber.empty", "please enter flight number");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "airlines", "flight.airlines.empty", "please enter airlines");

		if (flight.getAirlines() == null) {
			errors.rejectValue("airlines", "flight.airlines.empty", "select airline");
		}
		
		if (flight.getCapacity() <= 0) {
			errors.rejectValue("capacity", "flight.capacity.empty", "please enter flight capacity");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departureCity", "flight.departureCity.empty", "please enter departure city");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departureDate", "flight.departureDate.empty", "please enter departure date");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departureTime", "flight.departureTime.empty", "please enter departure time");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arrivalCity", "flight.arrivalCity.empty", "please enter arrival city");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arrivalDate", "flight.arrivalDate.empty", "please enter arrival date");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arrivalTime", "flight.arrivalTime.empty", "please enter arrival time");
	}

}
