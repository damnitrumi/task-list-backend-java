package com.guilhermesoares.tasklist.services;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.guilhermesoares.tasklist.services.exceptions.DatabaseException;
import com.guilhermesoares.tasklist.services.exceptions.ResourceNotFoundException;
import com.guilhermesoares.tasklist.services.exceptions.UnauthorizedException;

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
		Long userId = jwtService.recoverTokenId(request);
		List<Task> tasks = taskRepository.findByTaskOwnerId(userId);

		return tasks;
	}

	public Task insertTask(TaskRegisterDTO taskRegisterDTO, HttpServletRequest request) {
		Long userId = jwtService.recoverTokenId(request);
		Task task = new Task(taskRegisterDTO);

		User user = userService.findUserById(userId);
		task.setTaskOwner(user);
		taskRepository.save(task);

		return task;
	}

	public Task updateTask(Long taskId, TaskUpdateDTO taskUpdateDTO, HttpServletRequest request) {
		Long userId = jwtService.recoverTokenId(request);

		if (!userRepository.existsById(userId)) {
			throw new ResourceNotFoundException(userId);
		}

		Task task = findTaskOrThrow(taskId);

		checkUserPermission(userId, task, "User not authorized to update this task");

		task = updateTaskOBJ(task, taskUpdateDTO);

		taskRepository.save(task);

		return task;
	}

	public void deleteTask(Long taskId, HttpServletRequest request) {
		Long userId = jwtService.recoverTokenId(request);

		if (!userRepository.existsById(userId)) {
			throw new ResourceNotFoundException(userId);
		}

		Task task = findTaskOrThrow(taskId);

		checkUserPermission(userId, task, "User not authorized to delete this task");

		try {
			taskRepository.deleteById(taskId);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	//This method below could be moved to an util class, but i'll leave it here to not overhead/add complexity
	public List<TaskDTO> toTaskDTO(List<Task> tasks) {
		List<TaskDTO> tasksDTO = tasks.stream().map(TaskDTO::fromEntity).collect(Collectors.toList());
		return tasksDTO;
	}

	public Task updateTaskOBJ(Task task, TaskUpdateDTO taskUpdateDTO) {
		TaskStatus taskStatus = TaskStatus.valueOf(taskUpdateDTO.taskStatus().toUpperCase());
		TaskPriority taskPriority = TaskPriority.valueOf(taskUpdateDTO.taskPriority().toUpperCase());

		if (taskStatus != TaskStatus.COMPLETED && task.getCompletedAt() != null) {
			task.setCompletedAt(null);
		}

		if (taskStatus == TaskStatus.COMPLETED) {
			task.setCompletedAt(ZonedDateTime.now());
		}

		task.setName(taskUpdateDTO.name());
		task.setDescription(taskUpdateDTO.description());
		task.setTaskStatus(taskStatus);
		task.setTaskPriority(taskPriority);

		return task;

	}

	public Task findTaskOrThrow(Long taskId) {
		return taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException(taskId));
	}

	public void checkUserPermission(Long userId, Task task, String msg) {
		if (!userId.equals(task.getTaskOwner().getId())) {
			throw new UnauthorizedException(msg);
		}
	}

}
