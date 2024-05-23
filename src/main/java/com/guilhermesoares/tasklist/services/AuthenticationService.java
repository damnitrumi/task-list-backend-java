package com.guilhermesoares.tasklist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public String authenticate(String login, String password) {	
		var usernamePassword = new UsernamePasswordAuthenticationToken(login, password);
		var auth = this.authenticationManager.authenticate(usernamePassword);
		return jwtService.generateToken(auth);
	}
}
