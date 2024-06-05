package com.guilhermesoares.tasklist.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.guilhermesoares.tasklist.entities.Task;
import com.guilhermesoares.tasklist.entities.enums.TaskPriority;
import com.guilhermesoares.tasklist.entities.enums.TaskStatus;

public record TaskDTO(Long id, String name, String description, ZonedDateTime createdAt, ZonedDateTime completedAt,
		TaskStatus taskStatus, TaskPriority taskPriority) implements Serializable{
	
	public static TaskDTO fromEntity(Task task) {
		return new TaskDTO(task.getId(), task.getName(), task.getDescription(), task.getCreatedAt(), task.getCompletedAt(), task.getTaskStatus(), task.getTaskPriority());
	}

}
