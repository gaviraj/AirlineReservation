package com.synergisticit.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Airlines {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long airlinesId;
	
	private String airlinesName;
	
	private String airlinesCode;
	
	@JsonBackReference
	@OneToMany(mappedBy = "airlines")
	private List<Flight> flights = new ArrayList<>();
}
