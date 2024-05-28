package com.guilhermesoares.tasklist.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.guilhermesoares.tasklist.entities.User;

//DTO that is gonna be sent as response body to the creation of a new user
public record UserRegisteredDTO(Long id, String username,ZonedDateTime createdAt) implements Serializable{
	
	public static UserRegisteredDTO fromEntity(User user) {
		return new UserRegisteredDTO(user.getId() ,user.getUsername(), ZonedDateTime.now());
	}
}
