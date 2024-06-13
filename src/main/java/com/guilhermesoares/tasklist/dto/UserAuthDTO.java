package com.guilhermesoares.tasklist.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

//DTO that is gonna be used as a shape for the json that has to be sent by the user to auth
public record UserAuthDTO(
		@NotBlank(message = "Login can not be blank")
		String login, 
		@NotBlank(message = "Password can not be blank")
		String password) implements Serializable{
	private static final long serialVersionUID = 1L;
}
