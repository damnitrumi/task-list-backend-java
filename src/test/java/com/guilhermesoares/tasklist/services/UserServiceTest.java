package com.guilhermesoares.tasklist.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.guilhermesoares.tasklist.entities.User;
import com.guilhermesoares.tasklist.repository.UserRepository;
import com.guilhermesoares.tasklist.services.exceptions.ResourceNotFoundException;

public class UserServiceTest {

	@Mock
	UserRepository userRepository;
	
	@Mock
	PasswordEncoder passwordEncoder;
	
	@Mock
	JwtService jwtService;
	
	@InjectMocks
	UserService userService;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	};
	
	@Test
	@DisplayName("should register a new User")
	void insertNewUser() {
		User user = new User(1L, "Username", "Password");
		userService.registerUser(user);
		verify(userRepository, times(1)).save(user);
	};
	
	@Test
	@DisplayName("should not register a new User when the login already exists")
	void failToInsertANewUser() {
		User user = new User(1L, "Username", "Password");
		when(userRepository.existsByLogin(user.getLogin())).thenReturn(true);
		
		//O AssertThrows já verifica se a exception foi lançada, se não for ele já falha o meu teste
		Exception thrown = Assertions.assertThrows(IllegalArgumentException.class, () ->{
			userService.registerUser(user);
		});
		
		Assertions.assertEquals("Username already exists", thrown.getMessage());
	};
	
	@Test
	@DisplayName("should recover User data when the login is successful")
	void getUserDataWhenLoggedIn() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		when(jwtService.recoverTokenSubject(request)).thenReturn("username");
		
		userService.recoverUserData(request);
		verify(userRepository, times(1)).findByLogin("username");
	};
	
	@Test
	@DisplayName("should return User data when id is passed as parameter")
	void getUserDataWithId() {
		User user = new User(1L, "Username", "Password");
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		
		User returnedUser = userService.findUserById(1L);
		
		Assertions.assertEquals("Username", returnedUser.getLogin());
		verify(userRepository, times(1)).findById(1L);
	};
	
	@Test
	@DisplayName("should return an error when id is invalid/When user is not found")
	void failToGetUserDataWithId() {
		
		ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			userService.findUserById(2L);
		});
		
		
		Assertions.assertEquals("Resource not found! Id: 2", thrown.getMessage());
	};
}
