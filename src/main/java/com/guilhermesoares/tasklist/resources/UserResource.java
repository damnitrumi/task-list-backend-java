package com.guilhermesoares.tasklist.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guilhermesoares.tasklist.entities.User;
import com.guilhermesoares.tasklist.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping
	public ResponseEntity<List<User>> findAll(){
		User u1 = new User(1L, "UserOne", "12345");
		List<User> list = new ArrayList<>();
		list.add(u1);
		return ResponseEntity.ok().body(list);
	}
}
