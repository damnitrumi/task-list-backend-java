package com.guilhermesoares.tasklist.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

//DTO that sets the shape of the json the user has to send to register a new user
public record UserRegisterDTO(
		@NotBlank(message = "Login can not be blank")
		String login, 
		@NotBlank(message = "Password can not be blank")
		String password) implements Serializable{
	private static final long serialVersionUID = 1L;
}
