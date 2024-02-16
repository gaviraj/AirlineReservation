package com.synergisticit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.synergisticit.domain.Flight;
import com.synergisticit.domain.Gender;
import com.synergisticit.domain.Passenger;
import com.synergisticit.domain.Reservation;
import com.synergisticit.service.FlightService;
import com.synergisticit.service.PassengerService;
import com.synergisticit.service.ReservationService;

@Controller
public class ReservationController {
	
	@Autowired ReservationService reservationService;
	@Autowired FlightService flightService;
	@Autowired PassengerService passengerService;

	@RequestMapping("reservationForm")
	public ModelAndView reservationForm(Reservation reservation, @RequestParam Long flightId, @RequestParam Long passengerId) {
		System.out.println("ReservationController.reservationForm()...");
		ModelAndView mav = new ModelAndView("reservationForm");
		
		//flight = flightService.findById(flight.getFlightId());
		Flight flight = flightService.findById(flightId);
		Passenger passenger = passengerService.findById(passengerId);
		mav.addObject("flight", flight);
		mav.addObject("passenger", passenger);
		mav.addObject("genders", Gender.values());
		
		return mav;
	}
	
	@RequestMapping("saveReservation")
	public ModelAndView saveReservation(Reservation reservation) {
		System.out.println("ReservationController.saveReservation()...");
		Reservation savedReservation = reservationService.saveReservation(reservation);
		Flight flight = reservation.getFlight();
		flight.setSeatsBooked(flight.getSeatsBooked() + 1);
		flightService.saveFlight(flight);
		ModelAndView mav = new ModelAndView("redirect:reservationSuccess?reservationNumber=" + savedReservation.getReservationNumber());
		return mav;
	} 
	
	@RequestMapping("reservationSuccess")
	public ModelAndView reservationSuccess(@RequestParam Long reservationNumber) {
		System.out.println("ReservationController.reservationSuccess()...");
		ModelAndView mav = new ModelAndView("reservationSuccess");
		Reservation reservation = reservationService.findById(reservationNumber);
		mav.addObject("reservation", reservation);
		
		return mav;
	} 
	
	
	@RequestMapping("reservations")
	@Secured({"DBA", "Admin"})
	public ModelAndView reservations(Reservation reservation) {
		System.out.println("ReservationController.reservations()...");
		ModelAndView mav = new ModelAndView("reservations");
		mav.addObject("reservations", reservationService.findAll());
		//mav.addObject("roles", roleService.findAll());
		mav.addObject("activeReservations", "active");
		
		return mav;
	}
	
	@RequestMapping("checkInFlightAdmin")
	@Secured({"DBA", "Admin"})
	public ModelAndView checkInFlightAdmin(Reservation reservation) {
		System.out.println("ReservationController.checkInFlightAdmin()...");
		ModelAndView mav = new ModelAndView("reservations");
		
		if (reservation.getCheckedBags() < 0) {
			mav.addObject("errors", "enter number of checked bags");
		} else {
			int checkedBags = reservation.getCheckedBags();
			reservation = reservationService.findById(reservation.getReservationNumber());
			reservation.setCheckedBags(checkedBags);
			reservation.setCheckedIn(true);
			reservationService.saveReservation(reservation);
			mav.setViewName("redirect:reservations");
		}
		mav.addObject("reservations", reservationService.findAll());
		mav.addObject("activeReservations", "active");
		
		return mav;
	}
}
