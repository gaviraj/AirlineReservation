package com.synergisticit.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synergisticit.domain.Airlines;

public interface AirlinesService {

	public Airlines saveAirlines(Airlines airlines); 
	
	public List<Airlines> findAll();
	
	public Airlines findById(Long airlinesId);
	
	public void deleteById(Long airlinesId);
	
	public Page<Airlines> findAll(Pageable pageable);
}
