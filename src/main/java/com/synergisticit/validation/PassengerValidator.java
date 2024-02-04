package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Passenger;

@Component
public class PassengerValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Passenger.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Passenger passenger = (Passenger)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "passenger.firstName.empty", "please enter first name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "passenger.lastName.empty", "please enter last name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "passenger.email.empty", "please enter email");

		if (passenger.getDob() == null) {
			errors.rejectValue("dob", "passenger.dob.empty", "enter date of birth");
		}
		
		if (passenger.getGender() == null) {
			errors.rejectValue("gender", "passenger.gender.null", "select gender");
		} 
		
		if (
				passenger.getAddress().getAddressLine1().isEmpty() 
				|| passenger.getAddress().getCity().isEmpty()
				|| passenger.getAddress().getState().isEmpty()
				|| passenger.getAddress().getCountry().isEmpty()
				|| passenger.getAddress().getZipCode().isEmpty()
		) {
			errors.rejectValue("address", "passenger.address.empty", "please enter complete address");
		}

	}

}
