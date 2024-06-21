package com.guilhermesoares.tasklist.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import com.guilhermesoares.tasklist.dto.TaskRegisterDTO;
import com.guilhermesoares.tasklist.entities.Task;
import com.guilhermesoares.tasklist.entities.User;
import com.guilhermesoares.tasklist.repository.TaskRepository;
import com.guilhermesoares.tasklist.repository.UserRepository;

public class TaskServiceTest {
	
	@Mock
	TaskRepository taskRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	UserService userService;

	@Mock
	JwtService jwtService;
	
	@InjectMocks
	TaskService taskService;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	};
	
	@Test
	@DisplayName("should return a list of tasks based on User id")
	void getAllTasks() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		Long userId = 1L;
		
		when(jwtService.recoverTokenId(request)).thenReturn(userId);
		
		List<Task> expectedTasks = new ArrayList<>();
		expectedTasks.add(new Task());
		expectedTasks.add(new Task());
		
		when(taskRepository.findByTaskOwnerId(userId)).thenReturn(expectedTasks);
		
		List<Task> actualTasks = taskService.findTasksByUserId(request);
		
		verify(taskRepository, times(1)).findByTaskOwnerId(userId);
		Assertions.assertEquals(expectedTasks, actualTasks);
	}
	
	@Test
	@DisplayName("it should insert a task successfully")
	void insertTask() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		Long userId = 1L;
		
		when(jwtService.recoverTokenId(request)).thenReturn(userId);
		
		TaskRegisterDTO taskRegisterDTO = new TaskRegisterDTO("Name", "Description", "HIGH");
		User user = new User(userId, "Username", "Password");
		
		when(userService.findUserById(userId)).thenReturn(user);
		
		Task task = taskService.insertTask(taskRegisterDTO, request);
		verify(taskRepository, times(1)).save(any());
		
		Assertions.assertEquals(task.getTaskOwner().getId(), user.getId());
		Assertions.assertEquals(task.getName(), taskRegisterDTO.name());
		Assertions.assertEquals(task.getDescription(), taskRegisterDTO.description());
	}
}
