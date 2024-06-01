package com.guilhermesoares.tasklist.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guilhermesoares.tasklist.dto.TaskDTO;
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
}
