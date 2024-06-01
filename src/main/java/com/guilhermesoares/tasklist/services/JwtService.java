package com.guilhermesoares.tasklist.services;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.guilhermesoares.tasklist.entities.User;
import com.guilhermesoares.tasklist.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {

	@Autowired
	JwtEncoder encoder;
	
	@Autowired
	JwtDecoder decoder;
	
	@Autowired
	UserRepository userRepository;
	

	public String generateToken(Authentication authentication) {

		Instant now = Instant.now();
		long expire = 3600L;

		String scopes = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));
		
		Long id = ((User) userRepository.findByLogin(authentication.getName())).getId();

		var claims = JwtClaimsSet.builder().issuer("task-list").issuedAt(now).expiresAt(now.plusSeconds(expire))
				.subject(authentication.getName()).claim("scope", scopes).claim("id", id).build();

		return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}
	
	public String recoverTokenSubject(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if(authHeader == null) return null;
		String token = authHeader.replace("Bearer ", "");
		return decoder.decode(token).getSubject();
	}
	
	public Long recoverTokenId(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if(authHeader == null) return null;
		String token = authHeader.replace("Bearer ", "");
		return decoder.decode(token).getClaim("id");
	}
}
