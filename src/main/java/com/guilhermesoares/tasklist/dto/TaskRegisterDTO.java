package com.guilhermesoares.tasklist.dto;

import java.io.Serializable;

public record TaskRegisterDTO(String name, String description, String taskPriority) implements Serializable{

}
