package com.maslov.worklistproject.controller;

import com.maslov.worklistproject.entity.UserStats;
import com.maslov.worklistproject.service.UserStatsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/userstats")
public class UserStatsController {
    UserStatsService service;

    @GetMapping("/get/{id}")
    public ResponseEntity getInfoById(@PathVariable("id") Long id) {
        UserStats us = service.getInfoById(id);
        if (us != null) {
            return new ResponseEntity("Finished: " + us.getFinished() + "; in process: " + us.getInProcess() + ".", HttpStatus.OK);
        } else {
            return new ResponseEntity("Invalid id.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/get/byfinished")
    public Set<UserStats> findByFinished(@RequestBody Long finished) {
        return service.findByFinished(finished);
    }

    @PostMapping("/byemail")
    public UserStats findUserStatsByEmail(@RequestBody String email) {
        return service.findUserStatsByUserEmail(email);
    }
}
