package com.synergisticit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;
import com.synergisticit.service.RoleService;
import com.synergisticit.service.UserService;
import com.synergisticit.validation.UserValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class LoginController {
	
	@Autowired UserValidator userValidator;
	@Autowired UserService userService;
	@Autowired RoleService roleService;

	@RequestMapping("login")
	public String login(
			@RequestParam(required = false) String logout,
			@RequestParam(value = "error", required = false) String error,
			HttpServletRequest req, HttpServletResponse res, Model model) {
		System.out.println("LoginController.login()...");
		String message = null;
		if (logout != null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				new SecurityContextLogoutHandler().logout(req, res, auth);
				message = "you are logged out";
				model.addAttribute("message", message);
			}
		}
		
		if (error != null) {
			message = "either username or password is incorrect";
		}
		model.addAttribute("message", message);
		
		return "login";
	} 
	
	@RequestMapping("signUp")
	public String signUp(User user) {
		System.out.println("LoginController.signUp()...");
		return "signUp";
	}
	
	@RequestMapping("register")
	//public ModelAndView register(@Valid @ModelAttribute User user, BindingResult br) {
	public ModelAndView register(
			@RequestParam String username, 
			@RequestParam String password, 
			@RequestParam String email, 
			@RequestParam String phoneNum) {
		System.out.println("LoginController.register()...");
		ModelAndView mav = new ModelAndView("signUp");
		
		List<String> errors = new ArrayList<>();
		if (username.isEmpty()) {
			//mav.addObject("error", "please enter username");
			errors.add("please enter username");
		} else {
			if (userService.findByUsername(username) != null) {
				//mav.addObject("error", "username already exists");
				errors.add("username already exists");
			}			
		}
		if (password.isEmpty()) {
			//mav.addObject("error", "please enter password");
			errors.add("please enter password");
		}
		if (email.isEmpty()) {
			//mav.addObject("error", "please enter email");
			errors.add("please enter email");
		} else {
			if (userService.findByEmail(email) != null) {
				errors.add("user already exists with this email");
			}
		}
		if (phoneNum.isEmpty()) {
			//mav.addObject("error", "please enter phone number");
			errors.add("please enter phone number");
		}
		mav.addObject("errors", errors);
		
		if (errors.isEmpty()) {
			User newUser = new User();
			newUser.setUsername(username);
			newUser.setPassword(password);
			newUser.setEmail(email);
			newUser.setPhoneNum(phoneNum);
			
			List<Role> roles = new ArrayList<>();
			roles.add(roleService.findByRoleName("User"));
			newUser.setRoles(roles);
			
			userService.saveUser(newUser);
			
			mav.setViewName("login");
		} 
		
		return mav;
	}
	
	@RequestMapping("accessDenied")
	public String accessDenied() {
		return "accessDenied";
	} 
	
	@RequestMapping("underConstruction")
	public String underConstruction() {
		return "underConstruction";
	}
}
