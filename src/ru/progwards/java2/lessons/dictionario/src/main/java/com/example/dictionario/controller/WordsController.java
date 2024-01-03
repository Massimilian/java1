package com.example.dictionario.controller;

import com.example.dictionario.entity.Words;
import com.example.dictionario.service.WordsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/word")
public class WordsController {
    private final WordsService service;

    public WordsController(WordsService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Words getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/es/{word}")
    public String getByEsWord(@PathVariable String word) {
        return service.findByEsWord(word).getRu();
    }

    @PostMapping("/ru")
    public String getByRuWord(@RequestBody Words word) {
        return service.findByRuWord(word).getEs();
    }
}
