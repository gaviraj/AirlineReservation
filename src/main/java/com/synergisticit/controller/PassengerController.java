package com.synergisticit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.synergisticit.domain.Flight;
import com.synergisticit.domain.Gender;
import com.synergisticit.domain.Passenger;
import com.synergisticit.domain.Reservation;
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
		//mav.addObject("passengerList", passengers);
		
		return mav;
	}
	
	@RequestMapping("addPassengers")
	public ModelAndView totalPassengers(@Valid @ModelAttribute Passenger passenger, int numOfPassengers, @RequestParam Long flightId) {
		ModelAndView mav = new ModelAndView("passengerForm");
		Flight flight = flightService.findById(flightId);
		mav.addObject("flight", flight);
		mav.addObject("genders", Gender.values());
		
		List<Passenger> passengers = new ArrayList<Passenger>();
		System.out.println("num of pass: " + numOfPassengers);
		for (int i = 1; i <= numOfPassengers; i++) {
			passengers.add(new Passenger());
		}
		mav.addObject("passengerList", passengers);
		
		return mav;
	} 
	
	@RequestMapping("savePassengers")
	public ResponseEntity<?> savePassengesr(@RequestBody List<Passenger> passengers, @RequestParam Long flightId) {
		ModelAndView mav = new ModelAndView("passengerForm");
		List<Passenger> savedPassengers = new ArrayList<Passenger>();
		for (Passenger passenger : passengers) {
			Passenger newPassenger = passengerService.savePassenger(passenger);
			if (newPassenger != null) {
				savedPassengers.add(newPassenger);
			}
		}
		mav.addObject("passengers", savedPassengers);
		
		Flight flight = flightService.findById(flightId);
		
		if (savedPassengers.size() == passengers.size()) {
			StringBuilder redirectString = new StringBuilder("/reservationForm?flightId=" + flightId + "&passengerIds=");
			boolean more = true;
			for (int i = 0; i < savedPassengers.size(); i++) {
				redirectString.append(savedPassengers.get(i).getPassengerId());
				if (more == true && i < savedPassengers.size() - 1) {
					redirectString.append(",");
				} else {
					more = false;
				}
			}
			System.out.println(redirectString.toString());
			//mav.setViewName("redirect:reservationForm2?flightId=" + flightId + "&passengerIds=");
			//mav.setViewName(redirectString.toString());
			mav.addObject("redirect", redirectString);
			return new ResponseEntity<List<Passenger>>(savedPassengers, HttpStatus.OK);
		} else {
			mav.addObject("flight", flight);
			mav.addObject("genders", Gender.values());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
		//System.out.println("flightid: " + flightId);
		
		//return mav;
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
	
//	@RequestMapping("passengers")
//	@Secured({"DBA", "Admin"})
//	public ModelAndView passengers(Passenger passenger) {
//		System.out.println("PassengerController.passengers()...");
//		ModelAndView mav = new ModelAndView("passengers");
//		mav.addObject("passengers", passengerService.findAll());
//		mav.addObject("genders", Gender.values());
//		mav.addObject("activePassengers", "active");
//		
//		return mav;
//	}
	
	@RequestMapping("passengers")
	@Secured({"DBA", "Admin"})
	public ModelAndView reservationsPageable(
			Passenger passenger,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "7") int pageSize,
			@RequestParam(defaultValue = "passengerId") String sortedBy
	) {
		System.out.println("PassengerController.passengers()...");
		ModelAndView mav = new ModelAndView("passengers");
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<Passenger> pagedPassengers = passengerService.findAll(pageable);
		List<Passenger> passengers = pagedPassengers.getContent();
		mav.addObject("passengers", passengers);
		mav.addObject("genders", Gender.values());
		mav.addObject("activePassengers", "active");
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("sortedBy", sortedBy);
		mav.addObject("totalPages", pagedPassengers.getTotalPages());
		
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
	public ModelAndView updatePassenger(
			Passenger passenger,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "7") int pageSize,
			@RequestParam(defaultValue = "passengerId") String sortedBy
	) {
		System.out.println("PassengerController.updatePassenger()...");
		ModelAndView mav = new ModelAndView("passengers");
		passenger = passengerService.findById(passenger.getPassengerId());
		mav.addObject("passenger", passenger);
		//mav.addObject("passengers", passengerService.findAll());
		mav.addObject("genders", Gender.values());
		mav.addObject("passengerGender", passenger.getGender());
		mav.addObject("activePassengers", "active");
		mav.addObject("isUpdate", true);
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<Passenger> pagedPassengers = passengerService.findAll(pageable);
		List<Passenger> passengers = pagedPassengers.getContent();
		mav.addObject("passengers", passengers);
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("sortedBy", sortedBy);
		mav.addObject("totalPages", pagedPassengers.getTotalPages());
		
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
