package com.maslov.worklistproject.controller;

import com.maslov.worklistproject.entity.Priority;
import com.maslov.worklistproject.service.PriorityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/priority")
public class PriorityController {
    private PriorityService service;

    @GetMapping("/all")
    public List<Priority> getAll() {
        return service.getAll();
    }

    @PostMapping("/get")
    public Priority getById(@RequestBody Long id) {
        return service.getById(id);
    }

    @PutMapping("/edit")
    public ResponseEntity edit(@RequestBody Priority priority) {
        if (priority.getId() == 0 || priority.getId() == null) {
            return new ResponseEntity("Impossible operation", HttpStatus.CONFLICT);
        } else if (priority.getName() == null) {
            return new ResponseEntity("Name can't be null", HttpStatus.NOT_ACCEPTABLE);
        } else {
            service.edit(priority);
            return ResponseEntity.ok("Good work!");
        }
    }

    @PutMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority) {
        if (priority.getId() != null && priority.getId() != 0) {
            return new ResponseEntity("Id mustn'y be determinated", HttpStatus.CONFLICT);
        } else if (priority.getName() == null || priority.getColor() == null || priority.getName().equals("") || priority.getColor().equals("")) {
            return new ResponseEntity("Name and color can't be null", HttpStatus.NOT_ACCEPTABLE);
        } else {
            return ResponseEntity.ok(service.add(priority));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteById(@RequestBody Long id) {
        if (id == null && id == 0) {
            return new ResponseEntity("Impossible operation - id mustn/t be null", HttpStatus.FORBIDDEN);
        } else {
            service.deleteById(id);
            return new ResponseEntity("Done", HttpStatus.OK);
        }
    }

}
