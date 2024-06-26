package com.guilhermesoares.tasklist.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guilhermesoares.tasklist.dto.UserAuthDTO;
import com.guilhermesoares.tasklist.services.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthResource {

	@Autowired
	AuthenticationService authenticationService;
	
	@PostMapping
	public ResponseEntity<String> login(@Valid @RequestBody UserAuthDTO data) {
		String token = authenticationService.authenticate(data.login(), data.password());
		return ResponseEntity.ok().body(token);
	}
	
}
