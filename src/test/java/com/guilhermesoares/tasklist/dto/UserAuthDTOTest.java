package com.guilhermesoares.tasklist.dto;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserAuthDTOTest {
	
	@Test
	@DisplayName("it should not throw any violations")
	void userAuthDTOTest() {
		UserAuthDTO userAuthDTO = new UserAuthDTO("name", "password");
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Set<ConstraintViolation<UserAuthDTO>> violations = validator.validate(userAuthDTO);
		Assertions.assertTrue(violations.isEmpty());
	}
}
