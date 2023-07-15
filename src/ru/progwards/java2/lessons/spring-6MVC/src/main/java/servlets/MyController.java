package servlets;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class MyController {
    HashMap<String, String> people = new HashMap();

    {
        this.people.put("1", "One");
        this.people.put("2", "Two");
    }

    @GetMapping(value = "/get/{id}") // указываем в адресной строке ключ, по которому будем получать значение
    public String get(Model model, @PathVariable("id") String id){ // передаём ключ в качестве аргумента
        model.addAttribute("message", people.get(id)); // кладём сообщение в файл .jsp
        return "index"; // вызываем файл .jsp с сообщением
    }
    @GetMapping(value = "/getBy") // указываем адрес - без ссылки на параметры
    public String getBy(Model model, @RequestParam("id") String id) { // параметры мы будем доставать из самого запроса
        model.addAttribute("message", people.get(id)); // кладём сообщение в файл .jsp
        return "index"; // вызываем файл .jsp с сообщением
    }

    @GetMapping(value = "/")
    public String getTest() {
        return "test";
    }
}

