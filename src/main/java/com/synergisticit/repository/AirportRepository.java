package com.synergisticit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.synergisticit.domain.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long> {

	Page<Airport> findAll(Pageable pageable);
}
