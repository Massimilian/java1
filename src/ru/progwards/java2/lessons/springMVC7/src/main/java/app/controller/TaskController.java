package app.controller;

import app.services.Task;
import app.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class TaskController {
    @Autowired
    private TaskService service;

    @GetMapping(value = "/index")
    public String getTasks(Model model) {
        model.addAttribute("list", service.getAll());
        return "index";
    }

    @GetMapping(value = "/")
    public String getTest() {
        return "start";
    }

    @GetMapping(value = "/task/add")
    public String getAdd() {
        return "add";
    }

    // метод посылки сообщения - "post", поэтому аннотация теперь @PostMapping
    @PostMapping(value = "/task/add")
    // Теперь нам необходимо вернуть объект RedirectView, чтобы затем перейти на нужную страницу
    public RedirectView setAdd(@ModelAttribute Task task) {
        task.setId(service.getUnusedId()); // устанавливаем ID, который ещё не был использован.
        // добавляем полученный Task
        service.add(task);
        // перенаправляемся
        return new RedirectView("/index");
    }

    @GetMapping(value = "/task/delete")
    public RedirectView delete(@RequestParam("id") Long id) { // получить id как параметр get-запроса
        service.delete(id);
        return new RedirectView("/index");
    }

    @GetMapping(value = "task/edit/{id}")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("id", id);
        return "edit";
    }

    @PostMapping(value="task/edit")
    public RedirectView setEdit(@ModelAttribute Task task) {
        System.out.println();
        service.update(task);
        return new RedirectView("/index");
    }
}
