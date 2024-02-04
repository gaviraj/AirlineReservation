package com.synergisticit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.synergisticit.domain.Airlines;
import com.synergisticit.domain.Airport;
import com.synergisticit.domain.Flight;
import com.synergisticit.service.AirlinesService;
import com.synergisticit.service.AirportService;
import com.synergisticit.service.FlightService;
import com.synergisticit.validation.FlightValidator;

import jakarta.validation.Valid;

@Controller
@Secured({"DBA", "Admin"})
public class FlightController {
	
	@Autowired FlightService flightService;
	@Autowired AirlinesService airlinesService;
	@Autowired AirportService airportService;
	@Autowired FlightValidator flightValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(flightValidator);
	}

	@RequestMapping("flightForm")
	public ModelAndView flightsForm(Flight flight) {
		System.out.println("FlightController.flightsForm()...");
		ModelAndView mav = new ModelAndView("flightForm");
		mav.addObject("flights", flightService.findAll());
		mav.addObject("allAirlines", airlinesService.findAll());
		List<String> airportCities = new ArrayList<String>();
		for (Airport airport : airportService.findAll()) {
			airportCities.add(airport.getCity());
		}
		mav.addObject("cities", airportCities);
		mav.addObject("activeFlightForm", "active");
		
		return mav;
	}
	
	@RequestMapping("saveFlight")
	public ModelAndView saveFlight(@Valid @ModelAttribute Flight flight, BindingResult br) {
		System.out.println("FlightController.saveFlight()...");
		ModelAndView mav = new ModelAndView("flightForm");
		
		if (!br.hasErrors()) {
			//Airlines airlines = airlinesService.findById(flight.getAirlines().getAirlinesId());
			//List<Flight> airlinesFlights = airlines.getFlights();
			//airlinesFlights.add(flight);
			//airlines.setFlights(airlinesFlights);
			flightService.saveFlight(flight);
			mav.setViewName("redirect:flightForm");
		}
		mav.addObject("flights", flightService.findAll());
		mav.addObject("allAirlines", airlinesService.findAll());
		mav.addObject("activeFlightForm", "active");
		
		return mav;
	}
	
	@RequestMapping("updateFlight")
	public ModelAndView updateFlight(Flight flight) {
		System.out.println("FlightController.updateFlight()...");
		ModelAndView mav = new ModelAndView("flightForm");
		flight = flightService.findById(flight.getFlightId());
		mav.addObject("selectedDeparture", flight.getDepartureCity());
		System.out.println(flight.getDepartureCity());
		mav.addObject("selectedArrival", flight.getArrivalCity());
		mav.addObject("flight", flight);
		List<String> airportCities = new ArrayList<String>();
		for (Airport airport : airportService.findAll()) {
			airportCities.add(airport.getCity());
		}
		mav.addObject("cities", airportCities);
		mav.addObject("flights", flightService.findAll());
		mav.addObject("allAirlines", airlinesService.findAll());
		mav.addObject("activeFlightForm", "active");
		mav.addObject("isUpdate", true);
		
		return mav;
	}
	
	@RequestMapping("deleteFlight")
	public ModelAndView deleteFlight(Flight flight) {
		System.out.println("FlightController.deleteFlight()...");
		
		flightService.deleteById(flight.getFlightId());
		
		ModelAndView mav = new ModelAndView("redirect:flightForm");
		mav.addObject("flights", flightService.findAll());
		mav.addObject("allAirlines", airlinesService.findAll());
		mav.addObject("activeFlightForm", "active");
		
		return mav;
	}
}
