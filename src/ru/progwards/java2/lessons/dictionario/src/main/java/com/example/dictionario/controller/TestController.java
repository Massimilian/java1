package com.example.dictionario.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    // адрес для вызова через Postman: localhost:8081/test/get
    // приходящий ответ: "ОК"
    @GetMapping("/get")
    public ResponseEntity get() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // адрес для вызова через Postman: localhost:8081/test/post
    // тело сообщения: "какая-нибудь строка"
    // приходящий ответ: This is an info from the post method: "какая-нибудь строка"
    @PostMapping("/post")
    public ResponseEntity<String> post(@RequestBody String info) {
        return ResponseEntity.ok("This is an info from the post method: " + info);
    }

    // адрес для вызова через Postman: localhost:8081/test/delete/"какая-нибудь строка"
    // приходящий ответ: This is an info from @PathVariable from the delete method: "какая-нибудь строка"
    @DeleteMapping("/delete/{info}")
    public ResponseEntity delete(@PathVariable("info") String info) {
        return ResponseEntity.ok("This is an info from @PathVariable from the delete method: " + info);
    }

    // адрес для вызова через Postman: localhost:8081/test/put
    // тело сообщения: ["one", "two"]
    // приходящий ответ: This info is from the body of the put method: one, two

    @PutMapping("/put")
    public ResponseEntity<String> put(@RequestBody String[] info) {
        return ResponseEntity.ok("This info is from the body of the put method: " + info[0] + ", " + info[1]);
    }
}
