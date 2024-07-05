package com.guilhermesoares.tasklist.resources;

import static org.hamcrest.Matchers.empty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import com.guilhermesoares.tasklist.dto.TaskDTO;
import com.guilhermesoares.tasklist.entities.Task;
import com.guilhermesoares.tasklist.services.TaskService;

public class TaskResourceTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Mock
	TaskService taskService;
	
	@InjectMocks
	TaskResource taskResource;
	
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(taskResource).build();
		objectMapper = new ObjectMapper();
	}
	
	@Test
	@DisplayName("it should return a task list")
	void getTasksByUserId() throws Exception{
		List<Task> tasks = new ArrayList<>();
		List<TaskDTO> tasksDTO = new ArrayList<>();
		
		when(taskService.findTasksByUserId(any())).thenReturn(tasks);
		when(taskService.toTaskDTO(tasks)).thenReturn(tasksDTO);
		
		mockMvc.perform(get("/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer testtoken"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", empty()));
		
	}
}
