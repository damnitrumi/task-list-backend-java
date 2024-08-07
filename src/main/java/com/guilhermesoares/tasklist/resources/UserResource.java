package com.guilhermesoares.tasklist.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.guilhermesoares.tasklist.dto.UserDTO;
import com.guilhermesoares.tasklist.dto.UserRegisterDTO;
import com.guilhermesoares.tasklist.dto.UserRegisteredDTO;
import com.guilhermesoares.tasklist.entities.User;
import com.guilhermesoares.tasklist.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	UserService userService;

	@PostMapping("/register")
	public ResponseEntity<UserRegisteredDTO> register(@Valid @RequestBody UserRegisterDTO userRegister) {
		User user = new User(userRegister);
		User obj = userService.registerUser(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		UserRegisteredDTO userRegisteredDTO = UserRegisteredDTO.fromEntity(obj);
		return ResponseEntity.created(uri).body(userRegisteredDTO);
	}

	@GetMapping("/profile")
	public ResponseEntity<UserDTO> getCurrentUser(HttpServletRequest request){
		User user = userService.recoverUserData(request);
		UserDTO userDTO = UserDTO.fromEntity(user);
		return ResponseEntity.ok().body(userDTO);
	}
}
