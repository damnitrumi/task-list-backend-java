package com.guilhermesoares.tasklist.resources;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.empty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.guilhermesoares.tasklist.dto.TaskRegisterDTO;
import com.guilhermesoares.tasklist.entities.Task;
import com.guilhermesoares.tasklist.services.TaskService;

import jakarta.servlet.http.HttpServletRequest;

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
	
	@Test
	@DisplayName("it should insert a task")
	void insertTask() throws Exception{
		TaskRegisterDTO taskRegisterDTO = new TaskRegisterDTO("task", "desc", "HIGH");
		Task task = new Task(taskRegisterDTO);
		task.setId(1L);
		
		when(taskService.insertTask(eq(taskRegisterDTO), any(HttpServletRequest.class))).thenReturn(task);
		
		mockMvc.perform(post("/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(taskRegisterDTO))
				.header("Authorization", "Bearer testtoken"))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id").value(task.getId()))
		.andExpect(jsonPath("$.name").value(task.getName()))
		.andExpect(jsonPath("$.description").value(task.getDescription()))
		.andExpect(jsonPath("$.createdAt").exists())
		.andExpect(jsonPath("$.completedAt").value(nullValue()))
		.andExpect(jsonPath("$.taskStatus").value(task.getTaskStatus().toString()))
		.andExpect(jsonPath("$.taskPriority").value(task.getTaskPriority().toString()));
	}
}
