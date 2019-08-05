package com.ilknur.task.dao;

import com.ilknur.task.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ilknur.task.entities.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task ,Long>{
    public List<Task> findByUser(User user );
}
