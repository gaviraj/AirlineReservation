package com.synergisticit.dao;

import java.util.List;

import com.synergisticit.domain.User;

public interface UserDao {

	public User saveUser(User user); 
	
	public List<User> findAll();
	
	public User findById(Long userId);
	
	public void deleteById(Long userId);
	
	public User findByUsername(String username);
	
	User findByEmail(String email);
}
