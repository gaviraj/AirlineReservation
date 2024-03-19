package com.synergisticit.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synergisticit.domain.User;

public interface UserService {

	public User saveUser(User user); 
	
	public List<User> findAll();
	
	public User findById(Long userId);
	
	public void deleteById(Long userId);
	
	public User findByUsername(String username);
	
	User findByEmail(String email);
	
	Page<User> findAll(Pageable pageable);
}
