package com.guilhermesoares.tasklist.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import com.guilhermesoares.tasklist.dto.TaskRegisterDTO;
import com.guilhermesoares.tasklist.dto.TaskUpdateDTO;
import com.guilhermesoares.tasklist.entities.Task;
import com.guilhermesoares.tasklist.entities.User;
import com.guilhermesoares.tasklist.entities.enums.TaskPriority;
import com.guilhermesoares.tasklist.entities.enums.TaskStatus;
import com.guilhermesoares.tasklist.repository.TaskRepository;
import com.guilhermesoares.tasklist.repository.UserRepository;
import com.guilhermesoares.tasklist.services.exceptions.ResourceNotFoundException;
import com.guilhermesoares.tasklist.services.exceptions.UnauthorizedException;

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
	
	@Test
	@DisplayName("it should update a task")
	void updateTask() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		Long userId = 1L;
		
		when(jwtService.recoverTokenId(request)).thenReturn(userId);
		when(userRepository.existsById(userId)).thenReturn(true);
		
		User user = new User(1L, "Username", "Password");
		Task task = new Task(2L, "Task Name", "Task Desc", TaskPriority.HIGH, user);
		
		when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
		
		TaskUpdateDTO taskUpdateDTO = new TaskUpdateDTO(1L, "Updated Task Name", "Updated Task Desc", "IN_PROGRESS", "LOW");
		
		Task updatedTask = taskService.updateTask(task.getId(), taskUpdateDTO, request);
		
		verify(taskRepository, times(1)).save(task);
		
		Assertions.assertEquals(taskUpdateDTO.name(), updatedTask.getName());
		Assertions.assertEquals(taskUpdateDTO.description(), updatedTask.getDescription());
		Assertions.assertEquals(TaskStatus.valueOf(taskUpdateDTO.taskStatus()), updatedTask.getTaskStatus());
		Assertions.assertEquals(TaskPriority.valueOf(taskUpdateDTO.taskPriority()), updatedTask.getTaskPriority());
		Assertions.assertNull(updatedTask.getCompletedAt());
		
		taskUpdateDTO = new TaskUpdateDTO(1L, "Updated Task Name", "Updated Task Desc", "COMPLETED", "LOW");
		updatedTask = taskService.updateTask(task.getId(), taskUpdateDTO, request);
		
		Assertions.assertNotNull(updatedTask.getCompletedAt());
	}
	
	@Test
	@DisplayName("it should not update a task when user does not exists")
	void updateTaskCase2() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		Long userId = 2L;
		
		when(jwtService.recoverTokenId(request)).thenReturn(userId);
		
		TaskUpdateDTO taskUpdateDTO = new TaskUpdateDTO(1L, "Updated Task Name", "Updated Task Desc", "IN_PROGRESS", "LOW");
		
		ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			taskService.updateTask(5L, taskUpdateDTO, request);
		});
		
		Assertions.assertEquals("Resource not found! Id: 2", thrown.getMessage());
	}
	
	@Test
	@DisplayName("it should not update a task when task does not exists")
	void updateTaskCase3() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		Long userId = 2L;
		
		when(jwtService.recoverTokenId(request)).thenReturn(userId);
		when(userRepository.existsById(userId)).thenReturn(true);
		
		TaskUpdateDTO taskUpdateDTO = new TaskUpdateDTO(1L, "Updated Task Name", "Updated Task Desc", "IN_PROGRESS", "LOW");
		
		ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			taskService.updateTask(5L, taskUpdateDTO, request);
		});
		
		Assertions.assertEquals("Resource not found! Id: 5", thrown.getMessage());
	}
	
	@Test
	@DisplayName("it should not update a task when userId does not match taskOwnerId")
	void updateTaskCase4() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		Long userId = 2L;
		
		when(jwtService.recoverTokenId(request)).thenReturn(userId);
		when(userRepository.existsById(userId)).thenReturn(true);
		
		TaskUpdateDTO taskUpdateDTO = new TaskUpdateDTO(1L, "Updated Task Name", "Updated Task Desc", "IN_PROGRESS", "LOW");
		Task task = new Task(2L, "Task Name", "Task Desc", TaskPriority.HIGH, new User(1L, "Username", "Password"));
		
		when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
		
		UnauthorizedException thrown = Assertions.assertThrows(UnauthorizedException.class, ()->{
			taskService.updateTask(task.getId(), taskUpdateDTO, request);
		});
		
		Assertions.assertEquals("User not authorized to update this task", thrown.getMessage());
	}
}
