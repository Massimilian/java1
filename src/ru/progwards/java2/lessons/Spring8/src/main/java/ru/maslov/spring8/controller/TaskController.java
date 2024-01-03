package ru.maslov.spring8.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maslov.spring8.entity.PageTask;
import ru.maslov.spring8.service.TaskService;

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
        System.out.println();
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


}
