package com.synergisticit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.synergisticit.domain.Flight;
import com.synergisticit.domain.Gender;
import com.synergisticit.domain.Passenger;
import com.synergisticit.domain.User;
import com.synergisticit.service.FlightService;
import com.synergisticit.service.PassengerService;
import com.synergisticit.validation.PassengerValidator;

import jakarta.validation.Valid;

@Controller
public class PassengerController {

	@Autowired PassengerService passengerService;
	@Autowired FlightService flightService;
	@Autowired PassengerValidator passengerValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		//binder.addValidators(passengerValidator);
	}
	
	@RequestMapping("passengerForm")
	public ModelAndView passengerForm(Passenger passenger, @RequestParam Long flightId) {
		System.out.println("PassengerController.passengerForm()...");
		ModelAndView mav = new ModelAndView("passengerForm");
		
		System.out.println("flightid: " + flightId);
		Flight flight = flightService.findById(flightId);
		mav.addObject("flight", flight);
		mav.addObject("genders", Gender.values());
		
		return mav;
	}
	
	@RequestMapping("savePassenger")
	public ModelAndView savePassenger(@Valid @ModelAttribute Passenger passenger, @RequestParam Long flightId, BindingResult br, RedirectAttributes redirectAttributes) {
		System.out.println("PassengerController.savePassenger()...");
		passengerValidator.validate(passenger, br);
		ModelAndView mav = new ModelAndView("passengerForm");
		
		Flight flight = flightService.findById(flightId);
		if (!br.hasErrors()) {
			passenger = passengerService.savePassenger(passenger);
			//mav.setViewName("redirect:reservationForm");
			//redirectAttributes.addAttribute("flight", flight).addFlashAttribute("passenger", passenger);
			mav.setViewName("redirect:reservationForm?flightId=" + flightId + "&passengerId=" + passenger.getPassengerId());
		} else {
			mav.addObject("flight", flight);
			mav.addObject("genders", Gender.values());
		}
		
		return mav;
	}
	
	@RequestMapping("passengers")
	@Secured({"DBA", "Admin"})
	public ModelAndView passengers(Passenger passenger) {
		System.out.println("PassengerController.passengers()...");
		ModelAndView mav = new ModelAndView("passengers");
		mav.addObject("passengers", passengerService.findAll());
		mav.addObject("genders", Gender.values());
		mav.addObject("activePassengers", "active");
		
		return mav;
	}
	
	@RequestMapping("savePassengerAdmin")
	@Secured({"DBA", "Admin"})
	public ModelAndView savePassengerAdmin(@Valid @ModelAttribute Passenger passenger, BindingResult br) {
		System.out.println("PassengerController.savePassengerAdmin()...");
		ModelAndView mav = new ModelAndView("passengers");
		
		if (!br.hasErrors()) {
			passengerService.savePassenger(passenger);
			mav.setViewName("redirect:passengers");
		}
		mav.addObject("passengers", passengerService.findAll());
		mav.addObject("genders", Gender.values());
		mav.addObject("activePassengers", "active");
		
		return mav;
	}
	
	@RequestMapping("updatePassenger")
	@Secured({"DBA", "Admin"})
	public ModelAndView updatePassenger(Passenger passenger) {
		System.out.println("PassengerController.updatePassenger()...");
		ModelAndView mav = new ModelAndView("passengers");
		passenger = passengerService.findById(passenger.getPassengerId());
		mav.addObject("passenger", passenger);
		mav.addObject("passengers", passengerService.findAll());
		mav.addObject("genders", Gender.values());
		mav.addObject("passengerGender", passenger.getGender());
		mav.addObject("activePassengers", "active");
		mav.addObject("isUpdate", true);
		
		return mav;
	}
	
	@RequestMapping("deletePassenger")
	@Secured({"DBA", "Admin"})
	public ModelAndView deletePassenger(Passenger passenger) {
		System.out.println("PassengerController.deletePassenger()...");
		
		passengerService.deleteById(passenger.getPassengerId());
		
		ModelAndView mav = new ModelAndView("redirect:passengers");
		mav.addObject("passengers", passengerService.findAll());
		mav.addObject("genders", Gender.values());
		mav.addObject("activePassengers", "active");
		
		return mav;
	}
}
