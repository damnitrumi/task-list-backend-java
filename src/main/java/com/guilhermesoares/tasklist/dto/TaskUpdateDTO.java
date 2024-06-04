package com.guilhermesoares.tasklist.dto;

import java.io.Serializable;

public record TaskUpdateDTO(Long id, String name, String description, String taskStatus, String taskPriority) implements Serializable{

}
