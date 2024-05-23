package com.guilhermesoares.tasklist.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.guilhermesoares.tasklist.dto.UserRegisterDTO;
import com.guilhermesoares.tasklist.entities.User;
import com.guilhermesoares.tasklist.services.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
	UserService userService;
	
	@GetMapping
	public ResponseEntity<List<User>> findAll(){
		User u1 = new User(1L, "UserOne", "12345");
		List<User> list = new ArrayList<>();
		list.add(u1);
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody UserRegisterDTO userRegister){
		User user = new User(userRegister);
		User obj = userService.registerUser(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
}
