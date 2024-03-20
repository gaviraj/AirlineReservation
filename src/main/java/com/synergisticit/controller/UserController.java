package com.synergisticit.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.synergisticit.domain.Reservation;
import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;
import com.synergisticit.service.ReservationService;
import com.synergisticit.service.RoleService;
import com.synergisticit.service.UserService;
import com.synergisticit.validation.UserValidator;

import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired UserService userService;
	@Autowired RoleService roleService;
	@Autowired ReservationService reservationService;
	@Autowired UserValidator userValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(userValidator);
	}
	
	@RequestMapping("users")
	@Secured({"DBA", "Admin"})
	public ModelAndView users(
			User user,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "5") int pageSize,
			@RequestParam(defaultValue = "userId") String sortedBy
	) {
		System.out.println("UserController.users()...");
		ModelAndView mav = new ModelAndView("users");
		//mav.addObject("users", userService.findAll());
		mav.addObject("roles", roleService.findAll());
		mav.addObject("activeUsers", "active");
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<User> pagedUsers = userService.findAll(pageable);
		List<User> users = pagedUsers.getContent();
		mav.addObject("users", users);
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("sortedBy", sortedBy);
		mav.addObject("totalPages", pagedUsers.getTotalPages());
		
		return mav;
	}
	
	@RequestMapping("saveUser")
	public ModelAndView saveUser(@Valid @ModelAttribute User user, BindingResult br) {
		System.out.println("UserController.saveUser()...");
		ModelAndView mav = new ModelAndView("users");
		
		if (!br.hasErrors()) {
			userService.saveUser(user);
			mav.setViewName("redirect:users");
		}
		mav.addObject("users", userService.findAll());
		mav.addObject("roles", roleService.findAll());
		mav.addObject("activeUsers", "active");
		
		return mav;
	}
	
	@RequestMapping("updateUser")
	public ModelAndView updateUser(User user) {
		System.out.println("UserController.updateUser()...");
		ModelAndView mav = new ModelAndView("users");
		user = userService.findById(user.getUserId());
		mav.addObject("user", user);
		mav.addObject("userRoles", user.getRoles());
		mav.addObject("users", userService.findAll());
		mav.addObject("roles", roleService.findAll());
		mav.addObject("activeUsers", "active");
		mav.addObject("isUpdate", true);
		
		return mav;
	}
	
	@RequestMapping("deleteUser")
	@Secured({"DBA", "Admin"})
	public ModelAndView deleteUser(User user) {
		System.out.println("UserController.deleteUser()...");
		
		userService.deleteById(user.getUserId());
		
		ModelAndView mav = new ModelAndView("redirect:users");
		mav.addObject("users", userService.findAll());
		
		return mav;
	}
	
	@RequestMapping("userProfile")
	@Secured({"User", "Admin", "DBA"})
	public ModelAndView userProfile(User user, Reservation reservation, Principal principal) {
		System.out.println("UserController.userProfile()...");
		ModelAndView mav = new ModelAndView("userProfile");
		user = userService.findByUsername(principal.getName());
		mav.addObject("user", user);
		//List<Reservation> userReservations = reservationService.findByPassengerEmail(user.getEmail());
		
		List<Reservation> completedReservations = new ArrayList<Reservation>();
		for (Reservation r : reservationService.findByPassengerEmail(user.getEmail())) {
			if (r.isCheckedIn()) {
				completedReservations.add(r);
			}
		}
		
		List<Reservation> upcomingReservations = new ArrayList<Reservation>();
		for (Reservation r : reservationService.findByPassengerEmail(user.getEmail())) {
			if (!r.isCheckedIn()) {
				upcomingReservations.add(r);
			}
		}
		
		//mav.addObject("userReservations", userReservations);
		mav.addObject("completeReservations", completedReservations);
		mav.addObject("upcomingReservations", upcomingReservations);
		//mav.addObject("roles", roleService.findAll());
		mav.addObject("activeProfile", "active");
		
		return mav;
	}
	
	@RequestMapping("checkInFlight")
	@Secured({"User"})
	public ModelAndView checkInFlight(Reservation reservation, User user, Principal principal) {
		System.out.println("UserController.checkInFlight()...");
		ModelAndView mav = new ModelAndView("userProfile");
		
		if (reservation.getCheckedBags() < 0) {
			mav.addObject("errors", "enter number of checked bags");
		} else {
			int checkedBags = reservation.getCheckedBags();
			reservation = reservationService.findById(reservation.getReservationNumber());
			reservation.setCheckedBags(checkedBags);
			reservation.setCheckedIn(true);
			reservationService.saveReservation(reservation);
			mav.setViewName("redirect:userProfile");
		}
		user = userService.findByUsername(principal.getName());
		mav.addObject("user", user);
		List<Reservation> userReservations = reservationService.findByPassengerEmail(user.getEmail());
		mav.addObject("userReservations", userReservations);
		mav.addObject("activeProfile", "active");
		
		return mav;
	}
}
