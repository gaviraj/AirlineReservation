package com.synergisticit.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Role;

@Component
public class RoleValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Role.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Role role = (Role)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleName", "role.roleName.empty", "please enter role name");

		if (role.getRoleName() != null) {
			if (role.getRoleName().length() < 2 && role.getRoleName().length() != 0) {
				errors.rejectValue("roleName", "roleName.minLength", "name must be 2 or more characters");
			}
			if (role.getRoleName().length() > 30) {
				errors.rejectValue("roleName", "roleName.maxLength", "name must not exceed 30 characters");
			}			
		}
	}

}
