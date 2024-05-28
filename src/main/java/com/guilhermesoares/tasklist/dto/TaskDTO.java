package com.guilhermesoares.tasklist.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.guilhermesoares.tasklist.entities.Task;

public record TaskDTO(Long id, String name, String description, ZonedDateTime createdAt, ZonedDateTime completedAt,
		Integer taskStatus, Integer taskPriority) implements Serializable{
	
	public static TaskDTO fromEntity(Task task) {
		return new TaskDTO(task.getId(), task.getName(), task.getDescription(), task.getCreatedAt(), task.getCompletedAt(), task.getTaskStatus().getCode(), task.getTaskPriority().getCode());
	}

}
