package com.maslov.worklistproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/getMethod")
    public String get() { // Адрес обращения - http://localhost:8081/test/getMethod
        return "This is get method";
    }

    @PostMapping("/postMethod")
    public String post(@RequestBody String info) { // Адрес обращения - http://localhost:8081/test/getMethod; в тело сообщения необходимо положить информацию
        return "You have sent: " + info;
    }

    @DeleteMapping("/deleteMethod/{info}")
    public String delete(@PathVariable("info") String info) {// Адрес обращения - http://localhost:8081/test/deleteMethod/сообщение; сообщение придёт в метод и превратится в аргумент
        return "You have wrote " + info + " in the address.";
    }

    @PutMapping("/putMathod")
    public ResponseEntity<String> put(@RequestBody String[] info) { // Адрес обращения - http://localhost:8081/test/putMethod; в тело
        return ResponseEntity.ok("This info is from the body of the put method: " + info[0] + ", " + info[1]);
        // обращаем внимание, что на этот раз строку мы возвращаем через ResponseEntity.ok(). Вывод в PostMan будет такой же.
    }
}
