package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Airlines;
import com.synergisticit.repository.AirlinesRepository;

@Service
public class AirlinesServiceImp implements AirlinesService {
	
	@Autowired AirlinesRepository airlinesRepository;

	@Override
	public Airlines saveAirlines(Airlines airlines) {
		return airlinesRepository.save(airlines);
	}

	@Override
	public List<Airlines> findAll() {
		return airlinesRepository.findAll();
	}

	@Override
	public Airlines findById(Long airlinesId) {
		return airlinesRepository.findById(airlinesId).orElse(null);
	}

	@Override
	public void deleteById(Long airlinesId) {
		airlinesRepository.deleteById(airlinesId);

	}

	@Override
	public Page<Airlines> findAll(Pageable pageable) {
		return airlinesRepository.findAll(pageable);
	}

}
