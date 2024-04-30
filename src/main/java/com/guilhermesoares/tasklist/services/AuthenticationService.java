package com.guilhermesoares.tasklist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
	
	@Autowired
	JwtService jwtService;
	
	public String authenticate(Authentication authentication) {
		return jwtService.generateToken(authentication);
	}
}
