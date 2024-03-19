package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.synergisticit.dao.UserDao;
import com.synergisticit.domain.User;
import com.synergisticit.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {
	
	@Autowired UserRepository userRepository;
	@Autowired BCryptPasswordEncoder encoder;
	@Autowired UserDao userDao;
	
	@Override
	public User saveUser(User user) {
		String encryptedPassword = encoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		
		return userRepository.save(user);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
		//return userDao.findAll();
	}

	@Override
	public User findById(Long userId) {
		return userRepository.findById(userId).orElse(null);
		//return userDao.findById(userId);
	}

	@Override
	public void deleteById(Long userId) {
		//userRepository.deleteById(userId);
		userDao.deleteById(userId);

	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
		//return userDao.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		//return userRepository.findByEmail(email);
		return userDao.findByEmail(email);
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

}
