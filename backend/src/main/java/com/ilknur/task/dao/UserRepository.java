package com.ilknur.task.dao;

import com.ilknur.task.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository  extends JpaRepository<User,Long> {
	public User findByUsername(String username );
}
