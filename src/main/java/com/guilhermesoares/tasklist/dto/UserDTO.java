package com.guilhermesoares.tasklist.dto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.guilhermesoares.tasklist.entities.User;

public record UserDTO(Long id, String login, List<TaskDTO> tasks) implements Serializable{

	public static UserDTO fromEntity(User user) {
		List<TaskDTO> tasksDTO = user.getTasks().stream().map(TaskDTO::fromEntity).collect(Collectors.toList());
		return new UserDTO(user.getId(), user.getLogin(), tasksDTO);
	}
}
