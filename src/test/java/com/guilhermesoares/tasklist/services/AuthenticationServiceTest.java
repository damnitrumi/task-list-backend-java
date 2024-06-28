package com.guilhermesoares.tasklist.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;

public class AuthenticationServiceTest {
	
	@Mock
	JwtService jwtService;
	
	@Mock
	private AuthenticationManager authenticationManager;
	
	@InjectMocks
	AuthenticationService authenticationService;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	@DisplayName("it should generate a token")
	void authenticate() {
		String token = "testtoken";
		
		when(jwtService.generateToken(any())).thenReturn(token);
		
		String generatedToken = authenticationService.authenticate("username", "password");
		Assertions.assertEquals(token, generatedToken);
	}
}
