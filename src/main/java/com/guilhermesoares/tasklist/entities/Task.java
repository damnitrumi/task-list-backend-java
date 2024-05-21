package com.guilhermesoares.tasklist.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.guilhermesoares.tasklist.entities.enums.TaskStatus;

public class Task implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String description;
	private ZonedDateTime createdAt;
	private ZonedDateTime completedAt;
	private Integer taskStatus;
	private Integer taskPriority;
	
	public Task() {
	}

	public Task(Long id, String name, String description, ZonedDateTime createdAt, ZonedDateTime completedAt,
			Integer taskStatus, Integer taskPriority) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.createdAt = createdAt;
		this.completedAt = completedAt;
		this.taskStatus = taskStatus;
		this.taskPriority = taskPriority;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public ZonedDateTime getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(ZonedDateTime completedAt) {
		this.completedAt = completedAt;
	}

	public TaskStatus getTaskStatus() {
		return TaskStatus.valueOf(taskStatus);
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		if(taskStatus != null) {
			this.taskStatus = taskStatus.getCode();
		}
	}

	public Integer getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(Integer taskPriority) {
		this.taskPriority = taskPriority;
	}
	
	
	
	
}
