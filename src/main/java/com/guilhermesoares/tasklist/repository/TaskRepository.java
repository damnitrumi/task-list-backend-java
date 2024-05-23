package com.guilhermesoares.tasklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilhermesoares.tasklist.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

}
