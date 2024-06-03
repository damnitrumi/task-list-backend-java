package com.guilhermesoares.tasklist.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilhermesoares.tasklist.dto.TaskDTO;
import com.guilhermesoares.tasklist.dto.TaskRegisterDTO;
import com.guilhermesoares.tasklist.entities.Task;
import com.guilhermesoares.tasklist.entities.User;
import com.guilhermesoares.tasklist.repository.TaskRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtService jwtService;

	public List<TaskDTO> findTasksByUserId(Long id) {
		List<Task> tasks = taskRepository.findByTaskOwnerId(id);
		List<TaskDTO> tasksDTO = toTaskDTO(tasks);
		return tasksDTO;
	}
	
	public List<TaskDTO> findTasksByUserId(HttpServletRequest request) {
		Long id = jwtService.recoverTokenId(request);
		List<Task> tasks = taskRepository.findByTaskOwnerId(id);
		List<TaskDTO> tasksDTO = toTaskDTO(tasks);
		return tasksDTO;
	}
	
	public TaskDTO insertTask(TaskRegisterDTO taskRegisterDTO, HttpServletRequest request) {
		Long id = jwtService.recoverTokenId(request);
		Task task = new Task(taskRegisterDTO);
		
		User user = userService.findUserById(id);
		task.setTaskOwner(user);
		taskRepository.save(task);
		TaskDTO taskDTO = TaskDTO.fromEntity(task);
		return taskDTO;
	}
	
	public List<TaskDTO> toTaskDTO(List<Task> tasks){
		List<TaskDTO> tasksDTO = tasks.stream().map(TaskDTO::fromEntity).collect(Collectors.toList());
		return tasksDTO;
	}
}
