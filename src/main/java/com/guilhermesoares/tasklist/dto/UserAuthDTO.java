package com.guilhermesoares.tasklist.dto;

import java.io.Serializable;

public record UserAuthDTO(String login, String password) implements Serializable{
	private static final long serialVersionUID = 1L;
}
