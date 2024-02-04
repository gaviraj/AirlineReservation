package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Airport;

@Component
public class AirportValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Airport.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Airport airport = (Airport)target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "airportCode", "airlines.airportCode.empty", "please enter airport code");
		
		if (airport.getAirportCode() != null) {
			if (airport.getAirportCode().length() < 3 && airport.getAirportCode().length() != 0) {
				errors.rejectValue("airportCode", "airportCode.minLength", "airport code must be 3 or more characters");
			}
			if (airport.getAirportCode().length() > 3) {
				errors.rejectValue("airportCode", "airportCode.maxLength", "airport code must not exceed 3 characters");
			}			
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "airportName", "airlines.airportName.empty", "please enter airport name");
		
		if (airport.getAirportName() != null) {
			if (airport.getAirportName().length() < 3 && airport.getAirportName().length() != 0) {
				errors.rejectValue("getAirportName", "getAirportName.minLength", "airport name must be 3 or more characters");
			}
			if (airport.getAirportCode().length() > 50) {
				errors.rejectValue("getAirportName", "getAirportName.maxLength", "airport name must not exceed 50 characters");
			}			
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "airlines.city.empty", "please enter airport city");
		
		if (airport.getCity() != null) {
			if (airport.getCity().length() < 3 && airport.getCity().length() != 0) {
				errors.rejectValue("city", "city.minLength", "city must be 3 or more characters");
			}
			if (airport.getAirportCode().length() > 50) {
				errors.rejectValue("getAirportName", "getAirportName.maxLength", "city must not exceed 50 characters");
			}			
		}
	}

}
