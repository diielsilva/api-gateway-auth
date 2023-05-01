package com.diel.dev.taskservice.controllers;

import com.diel.dev.taskservice.entities.TaskEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @GetMapping("common")
    public ResponseEntity<List<TaskEntity>> getCommonTasks() {
        List<TaskEntity> tasks = List.of(new TaskEntity(1L, "Learn Microservices"),
                new TaskEntity(2L, "Learn Angular"));
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("secured")
    public ResponseEntity<List<TaskEntity>> getAdminTasks() {
        List<TaskEntity> tasks = List.of(new TaskEntity(3L, "Learn API Gateway Auth"));
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
