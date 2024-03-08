package com.synergisticit.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	//@RequestMapping(value = {"/", "flights"})
	public ModelAndView home(Flight flight) {
		System.out.println("HomeController.home()...");
		ModelAndView mav = new ModelAndView("flights");
		mav.addObject("activeFlights", "active");
		mav.addObject("flights", flightService.findAll());
		
		return mav;
	}
	
	@RequestMapping(value = {"/", "flights"})
	public ModelAndView homePageable(
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "4") int pageSize,
			@RequestParam(defaultValue = "departureDate") String sortedBy
	) {
		ModelAndView mav = new ModelAndView("flights");
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<Flight> pagedFlights = flightService.findAll(pageable);
		List<Flight> flights = pagedFlights.getContent();
		
		mav.addObject("flights", flights);
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("sortedBy", sortedBy);
		mav.addObject("totalPages", pagedFlights.getTotalPages());
		
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
			mav.addObject("dateInputError", "please enter arrival city");
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
