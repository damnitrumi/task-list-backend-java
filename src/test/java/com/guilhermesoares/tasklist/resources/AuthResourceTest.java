package com.guilhermesoares.tasklist.resources;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import com.guilhermesoares.tasklist.dto.UserAuthDTO;
import com.guilhermesoares.tasklist.services.AuthenticationService;

public class AuthResourceTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Mock
	AuthenticationService authenticationService;
	
	@InjectMocks
	AuthResource authResource;
	
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(authResource).build();
		objectMapper = new ObjectMapper();
	}
	
	@Test
	@DisplayName("it should return JWT Token")
	void login() throws Exception{
		UserAuthDTO userAuthDTO = new UserAuthDTO("username", "password");
		String expectedToken = "token";
		when(authenticationService.authenticate(userAuthDTO.login(), userAuthDTO.password())).thenReturn(expectedToken);
		
		mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userAuthDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedToken));
	}
}
