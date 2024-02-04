package com.synergisticit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synergisticit.domain.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
