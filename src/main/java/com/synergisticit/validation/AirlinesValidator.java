package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Airlines;

@Component
public class AirlinesValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Airlines.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Airlines airlines = (Airlines)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "airlinesName", "airlines.airlinesName.empty", "please enter airline name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "airlinesCode", "airlines.airlinesCode.empty", "please enter airline code");

		if (airlines.getAirlinesName() != null) {
			if (airlines.getAirlinesName().length() < 3 && airlines.getAirlinesName().length() != 0) {
				errors.rejectValue("airlinesName", "airlinesName.minLength", "airlines name must be 3 or more characters");
			}
			if (airlines.getAirlinesName().length() > 30) {
				errors.rejectValue("airlinesName", "airlinesName.maxLength", "airlines name must not exceed 30 characters");
			}			
		}
		
		if (airlines.getAirlinesCode() != null) {
			if (airlines.getAirlinesCode().length() < 3 && airlines.getAirlinesCode().length() != 0) {
				errors.rejectValue("airlinesCode", "airlinesCode.minLength", "airlines code must be 3 characters");
			}
			if (airlines.getAirlinesCode().length() > 3) {
				errors.rejectValue("airlinesCode", "airlinesCode.maxLength", "airlines code must not exceed 3 characters");
			}			
		}
	}

}
