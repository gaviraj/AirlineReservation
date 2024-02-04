package com.synergisticit.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.synergisticit.domain.Flight;
import com.synergisticit.service.FlightService;

@Controller
public class HomeController {
	
	@Autowired FlightService flightService;

	@RequestMapping(value = {"/", "flights"})
	public ModelAndView home(Flight flight) {
		System.out.println("HomeController.home()...");
		ModelAndView mav = new ModelAndView("flights");
		mav.addObject("activeFlights", "active");
		mav.addObject("flights", flightService.findAll());
		
		return mav;
	}
	
	@RequestMapping("searchFlights")
	public ModelAndView searchFlights(
			@RequestParam(value = "fromCity") String fromCity,
			@RequestParam(value = "toCity") String toCity,
			@RequestParam(value = "fromDate") String fromDate,
			@RequestParam(value = "toDate") String toDate
	) {
		System.out.println("HomeController.searchFlights()...");
		ModelAndView mav = new ModelAndView("flights");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ldFromDate = null;
		LocalDate ldToDate = null;
		
		if (fromCity.isEmpty()) {
			mav.addObject("dateInputError", "please enter departure city");
		} else if (toCity.isBlank()) {
			mav.addObject("dateInputError", "please enter arrival date");
		} else if (fromDate.isEmpty()) {
			mav.addObject("dateInputError", "please enter departure date");
		} else if (toDate.isEmpty()) {
			mav.addObject("dateInputError", "please enter arrival date");
		} else {
			ldFromDate = LocalDate.parse(fromDate, formatter);
			ldToDate = LocalDate.parse(toDate, formatter);
		}
		if (ldFromDate != null && ldToDate != null && ldToDate.isBefore(ldFromDate)) {
			mav.addObject("dateInputError", "arrival date must be equal or greater than departure date");
		} else {
			List<Flight> searchResults = flightService.searchFlights(fromCity, toCity, ldFromDate, ldToDate);
			mav.addObject("searchResults", searchResults);			
		}
		
		
		mav.addObject("searchFromCity", fromCity);
		mav.addObject("searchToCity", toCity);
		mav.addObject("searchFromDate", fromDate);
		mav.addObject("searchToDate", toDate);
		mav.addObject("activeFlights", "active");
		
		return mav;
	}
	
	@RequestMapping("admin")
	@Secured({"DBA", "Admin"})
	public ModelAndView admin() {
		System.out.println("HomeController.admin()...");
		ModelAndView mav = new ModelAndView("admin");
		mav.addObject("activeAdmin", "active");
		
		return mav;
	}
}
