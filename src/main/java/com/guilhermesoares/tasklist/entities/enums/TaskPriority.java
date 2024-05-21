package com.guilhermesoares.tasklist.entities.enums;

public enum TaskPriority {
	LOW(1),
	MEDIUM(2),
	HIGH(3);
	
	private int code;
	
	private TaskPriority(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static TaskPriority valueOf(int code) {
		for(TaskPriority value : TaskPriority.values()) {
			if(value.getCode() == code) {
				return value;
			}
		}
		
		throw new IllegalArgumentException("Invalid Priority Code");
	}

}
