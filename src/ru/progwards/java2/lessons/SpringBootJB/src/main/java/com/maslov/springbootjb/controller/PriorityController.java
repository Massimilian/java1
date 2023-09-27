package com.maslov.springbootjb.controller;

import com.maslov.springbootjb.entity.Priority;
import com.maslov.springbootjb.service.PriorityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/priority")
public class PriorityController {
    private final PriorityService service;

    public PriorityController(PriorityService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Priority findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority) {
        if (priority.getId() != null && priority.getId() != 0) {
            return new ResponseEntity("Id must be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getName() == null || priority.getColor() == null) {
            return new ResponseEntity("Missed parameters (name or color)", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(service.add(priority));
    }

    @PutMapping("/update")
    public ResponseEntity<Priority> update(@RequestBody Priority priority) {
        if (priority.getId() == null || priority.getId() == 0) {
            return new ResponseEntity("Impossible situation: id cannot be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getName() == null || priority.getName().trim().length() == 0 || priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("Some values (name or color) are null", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(service.add(priority));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        try {
            service.findById(id);
            service.deleteById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("Impossible operation: id " + id + " is not valid", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
