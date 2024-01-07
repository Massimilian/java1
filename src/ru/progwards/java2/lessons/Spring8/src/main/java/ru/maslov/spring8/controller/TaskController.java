package ru.maslov.spring8.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maslov.spring8.entity.Loginator;
import ru.maslov.spring8.entity.PageTask;
import ru.maslov.spring8.entity.Task;
import ru.maslov.spring8.entity.Userdata;
import ru.maslov.spring8.service.TaskService;
import ru.maslov.spring8.service.UserdataService;

import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/rest")
public class TaskController {
    private TaskService service;

    // localhost:8082/rest/project/list
//    {
//        "page": "0",
//        "count": "10"
//    }
    @PostMapping("/project/list")
    public ResponseEntity<Page<Task>> getAll(@RequestBody PageTask pt) {
        // Назначаем направление сортировки через специальный объект Sort.Direction
        Sort.Direction direction = Sort.Direction.ASC;

        // создаём объект типа sort, который будет отвечать за сортировку; в конструктор помещаем направление в виде объекта Sort.Direction (которое мы выяснили ранее), название колонки, по которой сортируем и название колонки для сортировки второго плана
        Sort sort = Sort.by(direction, "id");

        Integer pageNumber = pt.getPage();
        Integer pageSize = pt.getCount();

        // создаём объект типа PageRequest, который принимает в конструктор номер страницы, размер страницы и тип сортировки
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Task> request = service.getAllTasks(pageRequest);
        return ResponseEntity.ok(request);
    }

    // localhost:8082/rest/project/delete/3
    @GetMapping("/project/delete/{id}")
    public ResponseEntity<Task> delete(@PathVariable Integer id) {
        String result = service.deleteById(id) == null ? "Not found!" : "Deleted!";
        return new ResponseEntity(result, HttpStatus.OK);
    }

    // localhost:8082/rest/project/create
//{
//    "name": "Test Task",
//    "description": " Test Task without description",
//    "prefix": "BBB"
//}
    @PostMapping("/project/create")
    public ResponseEntity create(@RequestBody Task task) {
        task.setIsdone(task.getIsdone() != null && task.getIsdone()); // todo почему в БД не добавляется false по умолчанию?
        if (task.getId() == null || task.getId() == 0) {
            if (task.getName() == null || task.getName().trim().equals("")) {
                return new ResponseEntity("Conflict situation: name mustn't be null", HttpStatus.CONFLICT);
            }
            service.create(task);

            return new ResponseEntity(task, HttpStatus.OK);
        } else {
            return new ResponseEntity("Conflict situation: id must be null", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/task/{id}/change/status")
    public ResponseEntity changeStatus(@PathVariable Integer id, @RequestBody String status) {
        if (status.trim().length() > 8) {
            status = status.trim().substring(11, status.length() - 3);
        }
        if (status.equalsIgnoreCase("done") || status.equalsIgnoreCase("not done") || status.equalsIgnoreCase("true") || status.equalsIgnoreCase("false")) {
            try {
                Task temp = service.findById(id);
                temp.setIsdone(status.equalsIgnoreCase("true") || status.equalsIgnoreCase("done"));
                service.create(temp);
                return ResponseEntity.ok("");
            } catch (NoSuchElementException nsee) {
                return new ResponseEntity("Object not exists", HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity("Status can be only true/done or false/not done", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/task/{id}/add/author")
    public ResponseEntity setAuthor(@PathVariable Integer id, @RequestBody Loginator login) {
        try {
            Userdata ud = service.getAuthor(login.getLogin());
            Task forChange = service.findById(id);
            forChange.setUserdataid(ud.getId());
            forChange.setUserdata(ud);
            service.create(forChange);
        } catch (NoSuchElementException nsee) {
            return new ResponseEntity("There are no elements with such id", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("");
    }

    @PostMapping("/task/{id}/change/priority")
    public ResponseEntity setPriority(@PathVariable Integer id, @RequestBody String priority) {
        try {
            Task forChange = service.findById(id);
            forChange.setPriority(priority);
            service.create(forChange);
            return ResponseEntity.ok("");
        } catch (NoSuchElementException nsee) {
            return new ResponseEntity("There are no elements with such id", HttpStatus.BAD_REQUEST);
        }
    }
}
