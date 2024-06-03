package com.guilhermesoares.tasklist.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.guilhermesoares.tasklist.dto.TaskDTO;
import com.guilhermesoares.tasklist.dto.TaskRegisterDTO;
import com.guilhermesoares.tasklist.services.TaskService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskResource {
	
	@Autowired
	TaskService taskService;


	@GetMapping
	public ResponseEntity<List<TaskDTO>> getTasksByUserId(HttpServletRequest request){
		List<TaskDTO> tasks = taskService.findTasksByUserId(request);
		
		return ResponseEntity.ok().body(tasks);
	}
	
	@PostMapping
	public ResponseEntity<TaskDTO> insertTask(@RequestBody TaskRegisterDTO taskRegisterDTO, HttpServletRequest request){
		TaskDTO taskDTO = taskService.insertTask(taskRegisterDTO, request);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(taskDTO.id()).toUri();
		return ResponseEntity.created(uri).body(taskDTO);
	}
	
}
