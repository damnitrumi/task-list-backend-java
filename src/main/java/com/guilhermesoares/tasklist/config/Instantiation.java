package com.guilhermesoares.tasklist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.guilhermesoares.tasklist.entities.User;
import com.guilhermesoares.tasklist.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		User u1 = new User(null, "username", "$2y$12$LGG4zKbQPH5WVcv9Guxa5uYxYSG5Reb39cIRVa0vT1PYJ7gQ1TQK6");
		userRepository.save(u1);
	}

}
