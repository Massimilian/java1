package ru.maslov.spring8.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maslov.spring8.entity.Userdata;
import ru.maslov.spring8.service.UserdataService;

import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/rest/project")
public class UserdataController {
    private UserdataService service;

    @GetMapping("/{id}")
    public Userdata getUserdata(@PathVariable Integer id) {
        return service.get(id);
    }

    @PostMapping("/user/add/grant")
    public ResponseEntity add(@RequestBody Userdata ud) {
        if (ud.getId() == null) {
            service.add(ud);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity("Service conflict - id must be null", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/{id}/user/change/grant")
    public ResponseEntity change(@PathVariable Integer id, @RequestBody Userdata ud) {
        System.out.println();
        try {
            this.getUserdata(id);
        } catch(NoSuchElementException nsee) {
            return new ResponseEntity("There is no element with such id", HttpStatus.BAD_REQUEST);
        }
        ud.setId(id);
        service.update(ud);
        return ResponseEntity.ok("");
    }
}
