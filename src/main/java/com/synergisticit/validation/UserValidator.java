package com.synergisticit.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.User;
import com.synergisticit.service.UserService;

@Component
public class UserValidator implements Validator {
	
	@Autowired UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User)target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "user.username.empty", "please enter username");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.password.empty", "please enter password");
		
		if (userService.findByUsername(user.getUsername()) != null && userService.findById(user.getUserId()) == null) {
			errors.rejectValue("username", "username.exists", "username already exists");
		}
		
		if (user.getUsername() != null) {
			if (user.getUsername().length() < 3 && user.getUsername().length() != 0) {
				errors.rejectValue("username", "username.minLength", "username must be 3 or more characters");
			}
			if (user.getUsername().length() > 50) {
				errors.rejectValue("username", "username.maxLength", "username must not exceed 50 characters");
			}			
		}
		
		if (user.getPassword() != null) {
			if (user.getPassword().length() < 6 && user.getPassword().length() != 0) {
				errors.rejectValue("password", "password.minLength", "password must be 6 or more characters");
			}
			if (user.getPassword().length() > 50) {
				errors.rejectValue("password", "password.maxLength", "password must not exceed 50 characters");
			}
		}
		
		if (user.getRoles().isEmpty()) {
			errors.rejectValue("roles", "user.roles.empty", "select user role(s)");
		}
	}

}
