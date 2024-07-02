package com.guilhermesoares.tasklist.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guilhermesoares.tasklist.dto.UserDTO;
import com.guilhermesoares.tasklist.dto.UserRegisterDTO;
import com.guilhermesoares.tasklist.dto.UserRegisteredDTO;
import com.guilhermesoares.tasklist.entities.User;
import com.guilhermesoares.tasklist.services.UserService;

public class UserResourceTest {
	
	@Autowired
	MockMvc mockMvc;

	@Mock
	UserService userService;
	
	@InjectMocks
	UserResource userResource;
	
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setup(){
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
		objectMapper = new ObjectMapper();
	};
	
	@Test
	@DisplayName("it should register an User")
	void registerUser() throws Exception{
		UserRegisterDTO userRegisterDTO = new UserRegisterDTO("username", "password");
		User user = new User(userRegisterDTO);
		user.setId(1L);
		
		UserRegisteredDTO userRegisteredDTO = UserRegisteredDTO.fromEntity(user);
		
		when(userService.registerUser(any(User.class))).thenReturn(user);
		
		mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegisterDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userRegisteredDTO.id()))
                .andExpect(jsonPath("$.username").value(userRegisteredDTO.username()));
			
	}
	
	@Test
	@DisplayName("it should return user data")
	void getCurrentUser() throws Exception{
		User user = new User(1L, "username", "password");
		UserDTO userDTO = UserDTO.fromEntity(user);
		
		when(userService.recoverUserData(any())).thenReturn(user);
		
		mockMvc.perform(get("/users/profile")
	            .contentType(MediaType.APPLICATION_JSON)
	            .header("Authorization", "Bearer testtoken"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.id").value(userDTO.id()))
	            .andExpect(jsonPath("$.login").value(userDTO.login()))
	            .andExpect(jsonPath("$.tasks").isEmpty());
	}
}
