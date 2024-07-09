package com.guilhermesoares.tasklist.dto;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class TaskRegisterDTOTest {
	
	@Test
	@DisplayName("it should not throw any violations")
	void taskRegisterDTOTest() {
		TaskRegisterDTO taskRegisterDTO = new TaskRegisterDTO("name", "description", "high");
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Set<ConstraintViolation<TaskRegisterDTO>> violations = validator.validate(taskRegisterDTO);
		Assertions.assertTrue(violations.isEmpty());
	}
}
