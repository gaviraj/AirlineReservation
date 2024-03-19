package com.synergisticit.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.synergisticit.domain.Airlines;
import com.synergisticit.domain.Airport;
import com.synergisticit.domain.Role;
import com.synergisticit.service.AirportService;
import com.synergisticit.service.FlightService;
import com.synergisticit.validation.AirportValidator;

import jakarta.validation.Valid;

@Controller
@Secured({"DBA", "Admin"})
public class AirportController {

	@Autowired AirportService airportService;
	@Autowired FlightService flightService;
	@Autowired AirportValidator airportValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(airportValidator);
	}
	
//	@RequestMapping("airports")
//	public ModelAndView airports(Airport airport) {
//		System.out.println("AirportController.airports()...");
//		ModelAndView mav = new ModelAndView("airports");
//		mav.addObject("airports", airportService.findAll());
//		mav.addObject("flights", flightService.findAll());
//		mav.addObject("activeAirports", "active");
//		
//		return mav;
//	} 
	
	@RequestMapping("airports")
	public ModelAndView airports(
			Airport airport,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "7") int pageSize,
			@RequestParam(defaultValue = "airportId") String sortedBy
	) {
		System.out.println("AirportController.airports()...");
		ModelAndView mav = new ModelAndView("airports");
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<Airport> pagedAirports = airportService.findAll(pageable);
		List<Airport> airports = pagedAirports.getContent();
		mav.addObject("airports", airports);
		mav.addObject("flights", flightService.findAll());
		mav.addObject("activeAirports", "active");
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("sortedBy", sortedBy);
		mav.addObject("totalPages", pagedAirports.getTotalPages());
		
		return mav;
	} 
	
	@RequestMapping("saveAirport")
	public ModelAndView saveAirport(@Valid @ModelAttribute Airport airport, BindingResult br) {
		System.out.println("AirportController.saveAirport()...");
		ModelAndView mav = new ModelAndView("airports");
		
		if (!br.hasErrors()) {
			airportService.saveAirport(airport);
			mav.setViewName("redirect:airports");
		}
		mav.addObject("airports", airportService.findAll());
		mav.addObject("activeAirlines", "active");
		
		return mav;
	} 
	
	@RequestMapping("updateAirport")
	public ModelAndView updateAirport(
			Airport airport,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "7") int pageSize,
			@RequestParam(defaultValue = "airportId") String sortedBy
	) {
		System.out.println("AirportController.updateAirport()...");
		ModelAndView mav = new ModelAndView("airports");
		airport = airportService.findById(airport.getAirportId());
		mav.addObject("airport", airport);
		//mav.addObject("airports", airportService.findAll());
		mav.addObject("flights", flightService.findAll());
		mav.addObject("activeAirports", "active");
		mav.addObject("isUpdate", true);
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<Airport> pagedAirports = airportService.findAll(pageable);
		List<Airport> airports = pagedAirports.getContent();
		mav.addObject("airports", airports);
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("sortedBy", sortedBy);
		mav.addObject("totalPages", pagedAirports.getTotalPages());
		
		return mav;
	}
}
