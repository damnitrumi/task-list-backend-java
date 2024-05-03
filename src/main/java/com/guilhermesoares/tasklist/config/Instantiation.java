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
		
		User u1 = new User(null, "username", "$2a$10$tGKD4d9gFZvplwwWeOdESuy4KzzGAgHc23QRgxHaGJrvHsjtnzN.C");
		userRepository.save(u1);
	}

}
