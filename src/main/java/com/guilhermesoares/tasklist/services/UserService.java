package com.guilhermesoares.tasklist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilhermesoares.tasklist.entities.User;
import com.guilhermesoares.tasklist.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	@Autowired
	UserRepository repository;
	
	@Transactional
	public User registerUser(User user) {
		if(repository.existsByLogin(user.getLogin())) {
			throw new IllegalArgumentException("Username already exists");
		}
		return repository.save(user);
	}
}
