package com.maslov.springbootjb.controller;

import com.maslov.springbootjb.entity.UserStats;
import com.maslov.springbootjb.service.UserStatsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/userstats")
public class UserStatsController {
    public final UserStatsService service;

    @GetMapping("/{id}")
    public UserStats findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    // Получение всех элементов типа UserStats по значению поля finished
    @GetMapping("/finished/{finished}")
    public List<UserStats> findByFinished(@PathVariable("finished") long finished) {
        return service.findByFinished(finished);
    }

    @PostMapping("/byemail")
    public UserStats findUserStatsByUserEmail(@RequestBody String email) {
        return service.findUserStatsByUserEmail(email);
    }

}
