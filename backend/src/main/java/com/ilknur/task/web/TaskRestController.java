package com.ilknur.task.web;

import com.ilknur.task.dao.TaskRepository;
import com.ilknur.task.entities.Item;
import com.ilknur.task.entities.Status;
import com.ilknur.task.entities.Task;
import com.ilknur.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TaskRestController {
    @Autowired
    TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity save(@RequestBody Task task) {
        if (task.getName() != null && !task.getName().equals("")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return new ResponseEntity(taskService.save(authentication,task), HttpStatus.OK);
        } else {
            return new ResponseEntity("{\"result\":\"Name area cannot be null.\"}", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/tasks")
    public ResponseEntity getTaskList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity(taskService.getTaskList(authentication), HttpStatus.OK);
    }
    @GetMapping("/tasks/{id}")
    public ResponseEntity getTask(@PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity(taskService.getTask(authentication,id), HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity deleteTask(@PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        taskService.delete(authentication, id);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PutMapping("/tasks")
    public ResponseEntity updateTask(@RequestBody Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        taskService.save(authentication,task);
        return new ResponseEntity(HttpStatus.OK);
    }

}
