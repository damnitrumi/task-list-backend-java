package com.guilhermesoares.tasklist.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guilhermesoares.tasklist.services.AuthenticationService;

@RestController
@RequestMapping("/login")
public class AuthResource {
	
	@Autowired
	AuthenticationService authenticationService;
	
	@PostMapping
	public String authenticate(Authentication authentication) {
		return authenticationService.authenticate(authentication);
	}
}
