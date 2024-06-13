package com.guilhermesoares.tasklist.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//DTO that is gonna be used as a shape for the json that has to be sent by the user to register a new task
public record TaskRegisterDTO(
		@NotBlank(message = "Task name can not be blank")
		String name, 
		@NotNull(message = "Task description can not be null")
		String description, 
		@NotBlank(message = "Task priority can not be blank")
		String taskPriority) implements Serializable{

}
