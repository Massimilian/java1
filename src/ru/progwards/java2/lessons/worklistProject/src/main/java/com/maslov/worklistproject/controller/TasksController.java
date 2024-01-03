package com.maslov.worklistproject.controller;

import com.maslov.worklistproject.entity.Tasks;
import com.maslov.worklistproject.service.TasksService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TasksController {
    TasksService service;

    @GetMapping("/{id}")
    public Tasks get(@PathVariable("id") Integer id) {
        return service.get(id);
    }

    @PostMapping("/findByName")
    public Tasks get(@RequestBody String name) {
        return service.getByName(name);
    }

    @PutMapping("/new")
    public ResponseEntity add(@RequestBody Tasks task) {
        if (task.getId() == null || task.getId().equals(0)) {
            if (task.getName() == null || task.getName().trim().equals("")) {
                return new ResponseEntity("Name cannot be null", HttpStatus.NOT_ACCEPTABLE);
            } else {
                if (task.getCategoryId() == null || task.getCategoryId() == 0) {
                    return new ResponseEntity("Category id cannot be null", HttpStatus.NOT_ACCEPTABLE);
                } else {
                    if (task.getUserDataId() == null || task.getUserDataId() == 0) {
                        return new ResponseEntity("User data id can't be null", HttpStatus.NOT_ACCEPTABLE);
                    } else {
                        if (task.getPriorityId() == null || task.getPriorityId() == 0) {
                            task.setPriorityId(2);
                        }
                        return ResponseEntity.ok(service.add(task));
                    }
                }
            }
        } else {
            return new ResponseEntity("Impossible operation: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
