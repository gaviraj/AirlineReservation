package com.synergisticit.controller;

import java.util.ArrayList;
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

//	@RequestMapping("reservationForm")
//	public ModelAndView reservationForm(Reservation reservation, @RequestParam Long flightId, @RequestParam Long passengerId) {
//		System.out.println("ReservationController.reservationForm()...");
//		ModelAndView mav = new ModelAndView("reservationForm");
//		
//		//flight = flightService.findById(flight.getFlightId());
//		Flight flight = flightService.findById(flightId);
//		Passenger passenger = passengerService.findById(passengerId);
//		mav.addObject("flight", flight);
//		mav.addObject("passenger", passenger);
//		mav.addObject("genders", Gender.values());
//		
//		return mav;
//	}
	
	@RequestMapping("reservationForm")
	public ModelAndView reservationForm2(Reservation reservation, @RequestParam Long flightId, @RequestParam Long[] passengerIds) {
		System.out.println("ReservationController.reservationForm2()...");
		ModelAndView mav = new ModelAndView("reservationForm");
		
		//flight = flightService.findById(flight.getFlightId());
		Flight flight = flightService.findById(flightId);
		//Passenger passenger = passengerService.findById(passengerId);
		mav.addObject("flight", flight);
		
		List<Passenger> passengers = new ArrayList<Passenger>();
		for (Long passId : passengerIds) {
			Passenger foundPassenger = passengerService.findById(passId);
			if (foundPassenger != null) {
				passengers.add(foundPassenger);
			}
		}
		mav.addObject("passengers", passengers);
		mav.addObject("genders", Gender.values());
		
		return mav;
	}
	
	@RequestMapping("saveReservations")
	public ModelAndView saveReservations(
			@RequestParam Long[] passengers,
			@RequestParam("flight") Long flightId,
			@RequestParam int checkedBags,
			@RequestParam boolean checkedIn
			) {
		System.out.println("ReservationController.saveReservations()...");
		List<Reservation> reservations = new ArrayList<Reservation>();
		for (Long passId : passengers) {
			Reservation reservation = new Reservation();
			Passenger pass = passengerService.findById(passId);
			reservation.setPassenger(pass);
			reservation.setCheckedBags(checkedBags);
			reservation.setCheckedIn(checkedIn);
			reservation.setFlight(flightService.findById(flightId));
			Reservation savedReservation = reservationService.saveReservation(reservation);
			Flight flight = reservation.getFlight();
			flight.setSeatsBooked(flight.getSeatsBooked() + 1);
			flightService.saveFlight(flight);
			reservations.add(savedReservation);
		}
		StringBuilder redirectString = new StringBuilder("redirect:reservationSuccess?reservationNumber=");
		int index = 0;
		for (Reservation res : reservations) {
			if (index == 0) {
				redirectString.append(res.getReservationNumber());
			} else {
				redirectString.append("&reservationNumber="+res.getReservationNumber());
			}
			index++;
		}
		
		ModelAndView mav = new ModelAndView(redirectString.toString());
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
	public ModelAndView reservationSuccess(@RequestParam Long[] reservationNumber) {
		System.out.println("ReservationController.reservationSuccess()...");
		ModelAndView mav = new ModelAndView("reservationSuccess");
		List<Reservation> reservations = new ArrayList<Reservation>();
		for (Long resNum : reservationNumber) {
			Reservation reservation = reservationService.findById(resNum);
			reservations.add(reservation);
			
		}
		mav.addObject("reservations", reservations);
		
		return mav;
	} 
	
	
	//@RequestMapping("reservations")
//	@Secured({"DBA", "Admin"})
//	public ModelAndView reservations(Reservation reservation) {
//		System.out.println("ReservationController.reservations()...");
//		ModelAndView mav = new ModelAndView("reservations");
//		mav.addObject("reservations", reservationService.findAll());
//		//mav.addObject("roles", roleService.findAll());
//		mav.addObject("activeReservations", "active");
//		
//		return mav;
//	}
	
	@RequestMapping("reservations")
	@Secured({"DBA", "Admin"})
	public ModelAndView reservationsPageable(
			Reservation reservation,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "5") int pageSize,
			@RequestParam(defaultValue = "reservationNumber") String sortedBy
	) {
		System.out.println("ReservationController.reservations()...");
		ModelAndView mav = new ModelAndView("reservations");
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<Reservation> pagedReservations = reservationService.findAll(pageable);
		List<Reservation> reservations = pagedReservations.getContent();
		mav.addObject("reservations", reservations);
		//mav.addObject("roles", roleService.findAll());
		mav.addObject("activeReservations", "active");
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("sortedBy", sortedBy);
		mav.addObject("totalPages", pagedReservations.getTotalPages());
		
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
