package com.guilhermesoares.tasklist.dto;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserRegisterDTOTest {

	@Test
	@DisplayName("it should not throw any violations")
	void validateUserRegisterDTO() {
		UserRegisterDTO userRegisterDTO = new UserRegisterDTO("username", "password");
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(userRegisterDTO);
		Assertions.assertTrue(violations.isEmpty());
	}
}
