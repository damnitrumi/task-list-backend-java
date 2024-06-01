package com.guilhermesoares.tasklist.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.guilhermesoares.tasklist.entities.Task;
import com.guilhermesoares.tasklist.entities.User;
import com.guilhermesoares.tasklist.entities.enums.TaskPriority;
import com.guilhermesoares.tasklist.repository.TaskRepository;
import com.guilhermesoares.tasklist.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		User u1 = new User(null, "username", "$2a$10$tGKD4d9gFZvplwwWeOdESuy4KzzGAgHc23QRgxHaGJrvHsjtnzN.C");
		User u2 = new User(null, "username2", "$2a$10$tGKD4d9gFZvplwwWeOdESuy4KzzGAgHc23QRgxHaGJrvHsjtnzN.C");
		userRepository.saveAll(Arrays.asList(u1, u2));
		
		Task t1 = new Task(null, "Task 1 from User 1", "Desc Aleatória", TaskPriority.LOW, u1);
		Task t2 = new Task(null, "Task 2 from User 1", "Desc Aleatória", TaskPriority.MEDIUM, u1);
		Task t3 = new Task(null, "Task 3 from User 1", "Desc Aleatória", TaskPriority.HIGH, u1);
		
		Task t4 = new Task(null, "Task 1 from User 2", "Desc Aleatória 2", TaskPriority.LOW, u2);
		Task t5 = new Task(null, "Task 2 from User 2", "Desc Aleatória 2", TaskPriority.LOW, u2);
		
		taskRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5));
		
	}
}
