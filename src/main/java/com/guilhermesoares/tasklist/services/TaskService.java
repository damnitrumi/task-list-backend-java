package com.guilhermesoares.tasklist.services;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilhermesoares.tasklist.dto.TaskDTO;
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

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	JwtService jwtService;

	public List<Task> findTasksByUserId(HttpServletRequest request) {
		Long id = jwtService.recoverTokenId(request);
		List<Task> tasks = taskRepository.findByTaskOwnerId(id);

		return tasks;
	}

	public Task insertTask(TaskRegisterDTO taskRegisterDTO, HttpServletRequest request) {
		Long id = jwtService.recoverTokenId(request);
		Task task = new Task(taskRegisterDTO);

		User user = userService.findUserById(id);
		task.setTaskOwner(user);
		taskRepository.save(task);

		return task;
	}

	public Task updateTask(Long taskId, TaskUpdateDTO taskUpdateDTO, HttpServletRequest request) {
		User user = null;
		Task task = null;
		Long id = jwtService.recoverTokenId(request);

		try {
			user = userRepository.getReferenceById(id);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}

		try {
			task = taskRepository.getReferenceById(taskId);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(taskId);
		}

		if (!user.getId().equals(task.getTaskOwner().getId())) {
			throw new UnauthorizedException("User not authorized to update this task");
		}

		task = updateTaskOBJ(task, taskUpdateDTO);
		taskRepository.save(task);

		return task;
	}

	public List<TaskDTO> toTaskDTO(List<Task> tasks) {
		List<TaskDTO> tasksDTO = tasks.stream().map(TaskDTO::fromEntity).collect(Collectors.toList());
		return tasksDTO;
	}

	public Task updateTaskOBJ(Task task, TaskUpdateDTO taskUpdateDTO) {
		TaskStatus taskStatus = TaskStatus.valueOf(taskUpdateDTO.taskStatus().toUpperCase());
		TaskPriority taskPriority = TaskPriority.valueOf(taskUpdateDTO.taskPriority().toUpperCase());
		
		if(taskStatus != TaskStatus.COMPLETED && task.getCompletedAt() != null) {
			task.setCompletedAt(null);
		}
		
		if(taskStatus == TaskStatus.COMPLETED) {
			task.setCompletedAt(ZonedDateTime.now());
		}

		task.setName(taskUpdateDTO.name());
		task.setDescription(taskUpdateDTO.description());
		task.setTaskStatus(taskStatus);
		task.setTaskPriority(taskPriority);

		return task;
	}

}
