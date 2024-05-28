package com.guilhermesoares.tasklist.dto;

import java.io.Serializable;

//DTO that sets the shape of the json the user has to send
public record UserRegisterDTO(String login, String password) implements Serializable{
	private static final long serialVersionUID = 1L;
}
