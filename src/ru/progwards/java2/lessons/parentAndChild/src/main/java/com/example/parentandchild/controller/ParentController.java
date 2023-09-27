package com.example.parentandchild.controller;

import com.example.parentandchild.entity.Parent;
import com.example.parentandchild.service.ParentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("parent")
public class ParentController {
    ParentService service;

    public ParentController(ParentService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<Parent> add(@RequestBody Parent p) {
        if (p.getId() != null && p.getId() != 0) {
            return new ResponseEntity("redundant param (id must be null)", HttpStatus.NOT_ACCEPTABLE);
        }
        if (p.getName() == null || p.getName().trim().equals("")) {
            return new ResponseEntity("missed param - title must be not null", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(service.add(p));
    }

    @PostMapping("/findById")
    public ResponseEntity<Parent> findById(@RequestBody int id) {
        Parent p = null;
        try {
            p = service.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("There is no parent with such id", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(p);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Parent p) {
        if (p.getId() == null || p.getId() == 0) {
            return new ResponseEntity("missed param (id must be null)", HttpStatus.NOT_ACCEPTABLE);
        }
        if (p.getName() == null || p.getName().trim().length() == 0) {
            return new ResponseEntity("missed param - title must be not null", HttpStatus.NOT_ACCEPTABLE);
        }
        service.update(p);
        return new ResponseEntity("Parent has been upgraded", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        try {
            // проверяем – существует ли по данному id элемент (если нет – выбросится ошибка)
            service.findById(id);
            service.deleteById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id '" + id + "' has not found.", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity("Parent has been deleted", HttpStatus.OK);
    }

}
