package com.guilhermesoares.tasklist.dto;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class TaskUpdateDTOTest {
	
	@Test
	@DisplayName("it not throw any violations")
	void taskUpdateDTOTest() {
		TaskUpdateDTO taskUpdateDTO = new TaskUpdateDTO(1L, "name", "description", "pending", "high");
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Set<ConstraintViolation<TaskUpdateDTO>> violations = validator.validate(taskUpdateDTO);
		Assertions.assertTrue(violations.isEmpty());
	}
}
