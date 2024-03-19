package com.synergisticit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.synergisticit.domain.Airlines;

public interface AirlinesRepository extends JpaRepository<Airlines, Long> {

	Page<Airlines> findAll(Pageable pageable);
}
