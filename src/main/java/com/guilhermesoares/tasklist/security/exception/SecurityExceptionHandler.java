package com.guilhermesoares.tasklist.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class SecurityExceptionHandler {
	
	@ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException authenticationException) {
      
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha errada: " + authenticationException.getMessage());
    }
}
