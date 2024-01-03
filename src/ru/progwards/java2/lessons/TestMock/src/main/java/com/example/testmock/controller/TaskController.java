package com.example.testmock.controller;

import com.example.testmock.entity.Task;
import com.example.testmock.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("taskController")
public class TaskController {
    @Autowired
    TaskService service;

    @GetMapping("/testget")
    public ResponseEntity add() {
        return ResponseEntity.ok(service.testget());
    }

    @PostMapping("/add")
    public ResponseEntity<Task> save(@RequestBody Task task) {
        service.add(task);
        return ResponseEntity.ok(task);
    }

    @PostMapping("/getById")
    public ResponseEntity<Task> getById(@RequestBody int id) {
        Task task = service.getById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/get")
    public ResponseEntity<ArrayList<Task>> get() {
        ArrayList<Task> tasks = service.get();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody Task task) {
        if (service.update(task)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Task> delete(@RequestBody Long id) {
        Task deleted = service.delete(id);
        return ResponseEntity.ok(deleted);
    }
}
