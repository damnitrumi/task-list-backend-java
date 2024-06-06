package com.guilhermesoares.tasklist.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.guilhermesoares.tasklist.dto.TaskDTO;
import com.guilhermesoares.tasklist.dto.TaskRegisterDTO;
import com.guilhermesoares.tasklist.dto.TaskUpdateDTO;
import com.guilhermesoares.tasklist.entities.Task;
import com.guilhermesoares.tasklist.services.TaskService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskResource {

	@Autowired
	TaskService taskService;

	@GetMapping
	public ResponseEntity<List<TaskDTO>> getTasksByUserId(HttpServletRequest request) {
		List<Task> tasks = taskService.findTasksByUserId(request);
		List<TaskDTO> tasksDTO = taskService.toTaskDTO(tasks);
		return ResponseEntity.ok().body(tasksDTO);
	}

	@PostMapping
	public ResponseEntity<TaskDTO> insertTask(@RequestBody TaskRegisterDTO taskRegisterDTO,
			HttpServletRequest request) {
		Task task = taskService.insertTask(taskRegisterDTO, request);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(task.getId())
				.toUri();
		TaskDTO taskDTO = TaskDTO.fromEntity(task);
		return ResponseEntity.created(uri).body(taskDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskUpdateDTO taskUpdateDTO,
			HttpServletRequest request) {
		Task task = taskService.updateTask(id, taskUpdateDTO, request);
		TaskDTO taskDTO = TaskDTO.fromEntity(task);
		return ResponseEntity.ok().body(taskDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id, HttpServletRequest request){
		taskService.deleteTask(id, request);
		return ResponseEntity.noContent().build();
	}
}
