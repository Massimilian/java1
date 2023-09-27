package com.example.parentandchild.controller;

import com.example.parentandchild.entity.Child;
import com.example.parentandchild.service.ChildService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("child")
public class ChildController {
    ChildService service;

    public ChildController(ChildService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<Child> add(@RequestBody Child ch) {
        System.out.println();
        if (ch.getId() != null && ch.getId() != 0) {
            return new ResponseEntity("redundant param (id must be null)", HttpStatus.NOT_ACCEPTABLE);
        }
        if (ch.getName() == null || ch.getName().trim().equals("")) {
            return new ResponseEntity("missed param - title must be not null", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(service.add(ch));
    }

    @PostMapping("/findById")
    public ResponseEntity<Child> findById(@RequestBody int id) {
        Child ch = null;
        try { // проверка на случай, если подобный Child не будет найден
            ch = service.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("There is no child with such id", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(ch);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Child ch) {
        if (ch.getId() == null || ch.getId() == 0) {
            return new ResponseEntity("missed param (id must be null)", HttpStatus.NOT_ACCEPTABLE);
        }
        if (ch.getName() == null || ch.getName().trim().length() == 0) {
            return new ResponseEntity("missed param - title must be not null", HttpStatus.NOT_ACCEPTABLE);
        }
        service.update(ch);
        return new ResponseEntity("Child was upgraded", HttpStatus.OK);
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
        return new ResponseEntity("Child was deleted", HttpStatus.OK);
    }


}
