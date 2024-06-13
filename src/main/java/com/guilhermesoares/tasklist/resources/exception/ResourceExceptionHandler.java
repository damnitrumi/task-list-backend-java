package com.guilhermesoares.tasklist.resources.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.guilhermesoares.tasklist.services.exceptions.DatabaseException;
import com.guilhermesoares.tasklist.services.exceptions.ResourceNotFoundException;
import com.guilhermesoares.tasklist.services.exceptions.UnauthorizedException;
import com.guilhermesoares.tasklist.utils.StandardError;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	//Usada quando o usuário tenta se registar com um username já existente
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StandardError> usernameAlreadyExists(IllegalArgumentException e, HttpServletRequest request){
		String error = "Username not available";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	//Usada quando um usuário tenta acessar um recurso não existente
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);		
	}
	
	//Usada quando um usuário tenta deletar um registro na tabela que está sendo utilizada em outra tabela: Integridade referencial
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
		String error = "Database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	//Usada quando o usuário não tem permissão para atualizar ou deletar um recurso
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<StandardError> unauthorized(UnauthorizedException e, HttpServletRequest request){
		String error = "Unauthorized";
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validationException(MethodArgumentNotValidException e, HttpServletRequest request){
		String error = "Data is not valid";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		StringBuilder errorMessage = new StringBuilder();
		List <FieldError> errorsList = e.getBindingResult().getFieldErrors();
		
		for(FieldError f : errorsList) {
			errorMessage.append(f.getDefaultMessage()).append(";");
		}
		
		
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, errorMessage.toString(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
