package com.guilhermesoares.tasklist.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.guilhermesoares.tasklist.utils.StandardError;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class SecurityExceptionHandler {
	
	@ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<StandardError> handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Invalid Username/Password", "Invalid Username/Password", request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
