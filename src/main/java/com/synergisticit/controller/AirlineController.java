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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.synergisticit.domain.Airlines;
import com.synergisticit.domain.Airport;
import com.synergisticit.domain.Flight;
import com.synergisticit.service.AirlinesService;
import com.synergisticit.validation.AirlinesValidator;

import jakarta.validation.Valid;

@Controller
@Secured({"DBA", "Admin"})
public class AirlineController {

	@Autowired AirlinesService airlinesService;
	@Autowired AirlinesValidator airlinesValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(airlinesValidator);
	}
	
//	@RequestMapping("airlines")
//	public ModelAndView airlines(Airlines airlines) {
//		System.out.println("AirlineController.airlines()...");
//		ModelAndView mav = new ModelAndView("airlines");
//		mav.addObject("allAirlines", airlinesService.findAll());
//		mav.addObject("activeAirlines", "active");
//		
//		return mav;
//	}
	
	@RequestMapping("airlines")
	public ModelAndView airlinesPagable(
			Airlines airlines,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "7") int pageSize,
			@RequestParam(defaultValue = "airlinesId") String sortedBy
	) {
		System.out.println("AirlineController.airlinesPagable()...");
		ModelAndView mav = new ModelAndView("airlines");
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<Airlines> pagedAirlines = airlinesService.findAll(pageable);
		List<Airlines> allAirlines = pagedAirlines.getContent();
		mav.addObject("allAirlines", allAirlines);
		mav.addObject("activeAirlines", "active");
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("sortedBy", sortedBy);
		mav.addObject("totalPages", pagedAirlines.getTotalPages());
		
		return mav;
	}
	
	@RequestMapping("saveAirline")
	public ModelAndView saveAirline(@Valid @ModelAttribute Airlines airlines, BindingResult br) {
		System.out.println("AirlineController.saveAirline()...");
		ModelAndView mav = new ModelAndView("airlines");
		
		if (!br.hasErrors()) {
			airlinesService.saveAirlines(airlines);
			mav.setViewName("redirect:airlines");
		}
		mav.addObject("allAirlines", airlinesService.findAll());
		mav.addObject("activeAirlines", "active");
		
		return mav;
	}
	
	@RequestMapping("updateAirlines")
	public ModelAndView updateAirlines(
			Airlines airlines,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "7") int pageSize,
			@RequestParam(defaultValue = "airlinesId") String sortedBy
	) {
		System.out.println("AirlineController.updateAirlines()...");
		ModelAndView mav = new ModelAndView("airlines");
		airlines = airlinesService.findById(airlines.getAirlinesId());
		mav.addObject("airlines", airlines);
		//mav.addObject("allAirlines", airlinesService.findAll());
		mav.addObject("activeAirlines", "active");
		mav.addObject("isUpdate", true);
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<Airlines> pagedAirlines = airlinesService.findAll(pageable);
		List<Airlines> allAirlines = pagedAirlines.getContent();
		mav.addObject("allAirlines", allAirlines);
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("sortedBy", sortedBy);
		mav.addObject("totalPages", pagedAirlines.getTotalPages());
		
		return mav;
	}
	
	@RequestMapping("deleteAirlines")
	public ModelAndView deleteAirlines(Airlines airlines) {
		System.out.println("AirlineController.deleteAirlines()...");
		
		airlinesService.deleteById(airlines.getAirlinesId());
		
		ModelAndView mav = new ModelAndView("redirect:airlines");
		mav.addObject("allAirlines", airlinesService.findAll());
		mav.addObject("activeAirlines", "active");
		
		return mav;
	}
}
