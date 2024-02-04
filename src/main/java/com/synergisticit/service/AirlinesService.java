package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.Airlines;

public interface AirlinesService {

	public Airlines saveAirlines(Airlines airlines); 
	
	public List<Airlines> findAll();
	
	public Airlines findById(Long airlinesId);
	
	public void deleteById(Long airlinesId);
}
