package com.guilhermesoares.tasklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.guilhermesoares.tasklist.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	UserDetails findByLogin(String login);
	
	boolean existsByLogin(String login);
}
