package com.guilhermesoares.tasklist.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guilhermesoares.tasklist.dto.UserAuthDTO;
import com.guilhermesoares.tasklist.services.AuthenticationService;

@RestController
@RequestMapping("/login")
public class AuthResource {

	@Autowired
	AuthenticationService authenticationService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping
	public ResponseEntity<String> login(@RequestBody UserAuthDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		String token = authenticationService.authenticate(auth);
		return ResponseEntity.ok().body(token);
	}
}
