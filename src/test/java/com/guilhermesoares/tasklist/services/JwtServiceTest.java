package com.guilhermesoares.tasklist.services;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import com.guilhermesoares.tasklist.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

public class JwtServiceTest {
	
	@Mock
    JwtEncoder encoder;

    @Mock
    JwtDecoder decoder;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    JwtService jwtService;

    @Mock
    Authentication authentication;

    @Mock
    HttpServletRequest request;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    @DisplayName("it should recover token subject")
    void recoverTokenSubject() {
    	when(request.getHeader("Authorization")).thenReturn("Bearer mockToken");
    	
    	Jwt jwt = Jwt.withTokenValue("mockToken")
    			.header("typ", "JWT")
    			.claim("sub", "user")
    			.build();
    	
    	when(decoder.decode("mockToken")).thenReturn(jwt);
    	
    	String subject = jwtService.recoverTokenSubject(request);
    	
    	Assertions.assertEquals("user", subject);
    }
    
    @Test
	@DisplayName("should return null if auth header is missing. We are doing this test but Spring Security would not let this method be called if there was no header")
	void recoverTokenSubjectCase2() {
    	when(request.getHeader("Authorization")).thenReturn(null);
    	
    	String subject = jwtService.recoverTokenSubject(request);
    	
    	Assertions.assertNull(subject);
    }
	@Test
    @DisplayName("it should recover token id")
    void recoverTokenId() {
        when(request.getHeader("Authorization")).thenReturn("Bearer mockToken");
        
        Jwt jwt = Jwt.withTokenValue("mockToken")
            .header("typ", "JWT")
            .claim("id", 1L)
            .build();
        
        when(decoder.decode("mockToken")).thenReturn(jwt);
        
        Long id = jwtService.recoverTokenId(request);
        
        Assertions.assertEquals(1L, id);
    }
	
	@Test
	@DisplayName("should return null if auth header is missing. We are doing this test but Spring Security would not let this method be called if there was no header")
	void recoverTokenIdCase2() {
		when(request.getHeader("Authorization")).thenReturn(null);
		
		Long id = jwtService.recoverTokenId(request);
		
		Assertions.assertNull(id);
	}
}
