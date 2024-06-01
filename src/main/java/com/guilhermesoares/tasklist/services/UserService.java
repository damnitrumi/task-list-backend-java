package com.guilhermesoares.tasklist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.guilhermesoares.tasklist.entities.User;
import com.guilhermesoares.tasklist.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtService jwtService;
	
	//Insert
	@Transactional
	public User registerUser(User user) {
		if(userRepository.existsByLogin(user.getLogin())) {
			throw new IllegalArgumentException("Username already exists");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return userRepository.save(user);
	}
	
	//Recover User data through JWT
	public User recoverUserData(HttpServletRequest request) {
		String subject = jwtService.recoverTokenSubject(request);
		UserDetails userDetails = userRepository.findByLogin(subject);
		User user = (User) userDetails;
		return user;
	}
}
