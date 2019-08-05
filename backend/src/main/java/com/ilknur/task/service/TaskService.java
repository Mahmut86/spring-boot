package com.ilknur.task.service;

import com.ilknur.task.dao.ItemRepository;
import com.ilknur.task.dao.TaskRepository;
import com.ilknur.task.entities.Task;
import com.ilknur.task.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    ItemRepository itemRepository;

    public Task save(Authentication authentication, Task task) {
        User user = accountService.findUserByUserName(authentication.getName());
        task.setUser(user);
        Task task1 = taskRepository.save(task);
        return task1;
    }

    public List<Task> getTaskList(Authentication authentication) {
        User user = accountService.findUserByUserName(authentication.getName());
        return taskRepository.findByUser(user);
    }

    public void delete(Authentication authentication, long id) {
        User user = accountService.findUserByUserName(authentication.getName());
        Task task = taskRepository.getOne(id);
        if (task.getUser() == user) {
            taskRepository.delete(task);
        }
    }


    public Task getTask(Authentication authentication, long id) {
        return taskRepository.getOne(id);
    }


}
