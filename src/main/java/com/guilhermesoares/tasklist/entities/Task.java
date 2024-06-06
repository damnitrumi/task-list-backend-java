package com.guilhermesoares.tasklist.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guilhermesoares.tasklist.dto.TaskRegisterDTO;
import com.guilhermesoares.tasklist.entities.enums.TaskPriority;
import com.guilhermesoares.tasklist.entities.enums.TaskStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_task")
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private ZonedDateTime createdAt;
	private ZonedDateTime completedAt;
	private Integer taskStatus;
	private Integer taskPriority;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User taskOwner;

	public Task() {
	}

	public Task(Long id, String name, String description, TaskPriority taskPriority, User taskOwner) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.createdAt = ZonedDateTime.now();
		this.completedAt = null;
		this.taskStatus = TaskStatus.PENDING.getCode();
		this.taskPriority = taskPriority.getCode();
		this.taskOwner = taskOwner;
	}
	
	public Task(TaskRegisterDTO taskRegisterDTO) {
		super();
		name = taskRegisterDTO.name();
		description = taskRegisterDTO.description();
		createdAt = ZonedDateTime.now();
		taskStatus = TaskStatus.PENDING.getCode();
		taskPriority = TaskPriority.valueOf(taskRegisterDTO.taskPriority().toUpperCase()).getCode();
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
		return TaskStatus.fromValue(taskStatus);
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		if (taskStatus != null) {
			this.taskStatus = taskStatus.getCode();
		}
	}

	public TaskPriority getTaskPriority() {
		return TaskPriority.fromValue(taskPriority);
	}

	public void setTaskPriority(TaskPriority taskPriority) {
		if (taskPriority != null) {
			this.taskPriority = taskPriority.getCode();
		}
	}
	
	public User getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(User taskOwner) {
		this.taskOwner = taskOwner;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return Objects.equals(id, other.id);
	}
}
