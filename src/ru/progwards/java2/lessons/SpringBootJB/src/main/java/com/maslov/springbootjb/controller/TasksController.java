package com.maslov.springbootjb.controller;

import com.maslov.springbootjb.entity.Tasks;
import com.maslov.springbootjb.search.PeriodTimestamps;
import com.maslov.springbootjb.search.TaskSearcher;
import com.maslov.springbootjb.service.TasksService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    private TasksService service;

    public TasksController(TasksService service) {
        this.service = service;
    }

    // Метод по поиску Tasks по полю userDataId, упорядочивающий по полю name
    @GetMapping("/{id}")
    public List<Tasks> findByUserId(@PathVariable("id") Integer userId) {
        return service.findByUserId(userId);
    }

    // Метод по поиску Tasks по полю dateandtime, принимающее два значения и ищущее все значения в промежутке
    @PostMapping("/between")
    public ResponseEntity<List<Tasks>> findByDateandtimeBetween(@RequestBody PeriodTimestamps pts) {
        if (pts.getFrom() == null || pts.getTo() == null) {
            return new ResponseEntity("Incorrect request: please enter all values", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(service.findByDateandtimeBetween(pts.getFrom(), pts.getTo()));
    }

    // Метод по поиску Tasks по имени (name)
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Tasks>> findByName(@PathVariable ("name") String name) {
        if (name == null || name.trim().equals("")) {
            return new ResponseEntity("Incorrect request: name mustn't be null", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(service.findByName(name));
    }

    @PostMapping("/add")
    public ResponseEntity<Tasks> add(@RequestBody Tasks tasks) {
        if (tasks.getId() != null && tasks.getId() != 0) {
            return new ResponseEntity("redundant param (id must be null)", HttpStatus.NOT_ACCEPTABLE);
        }
        tasks.setDateandtime(Timestamp.from(Instant.now()));
        if (tasks.getDone() == null) {
            tasks.setDone(false);
        }
        if (tasks.getCategoryId() == null) {
            tasks.setCategoryId(1);
        }
        if (tasks.getPriorityId() == null) {
            tasks.setPriorityId(1);
        }
        return ResponseEntity.ok(service.add(tasks));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Tasks tasks) {
        if (tasks.getId() == null || tasks.getId() == 0) {
            return new ResponseEntity("missed param (id must be null)", HttpStatus.NOT_ACCEPTABLE);
        }
        if (tasks.getName() == null || tasks.getName().trim().length() == 0) {
            return new ResponseEntity("missed param - title must be not null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (tasks.getDone() == null) {
            tasks.setDone(false);
        }
        if (tasks.getCategoryId() == null) {
            tasks.setCategoryId(1);
        }
        if (tasks.getPriorityId() == null) {
            tasks.setPriorityId(1);
        }
        service.update(tasks);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/byemail")
    public ResponseEntity<List<Tasks>> findByUserEmail(@RequestBody String email) {
        return ResponseEntity.ok(service.findByUserEmail(email));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Tasks>> search(@RequestBody TaskSearcher searcher) {
        // получаем все параметры из объекта TaskSearcher
        String name = searcher.getName() != null? searcher.getName() : null;
        Boolean done = searcher.getDone() != null? searcher.getDone() : null;
        Integer categoryId = searcher.getCategoryId() != null? searcher.getCategoryId() : null;
        Integer priorityId = searcher.getPriorityId() != null? searcher.getPriorityId() : null;
        Date dateFrom = searcher.getDateFrom() != null? searcher.getDateFrom() : null;
        // добавляем время в отправную дату, чтобы отсчёт шёл с определённого времени - 00:00:00
        if (dateFrom != null) {
            Calendar cfrom = Calendar.getInstance();
            cfrom.set(Calendar.HOUR_OF_DAY, 0); // устанавливаем час в ноль
            cfrom.set(Calendar.MINUTE, 0); // устанавливаем минуты в ноль
            cfrom.set(Calendar.SECOND, 0); // устанавливаем секунды в ноль
            cfrom.set(Calendar.MILLISECOND, 0); // устанавливаем миллисекунды в ноль
            dateFrom = cfrom.getTime(); // устанавливаем нолевое время в запрашиваемую дату
        }
        Date dateTo = searcher.getDateTo() != null? searcher.getDateTo() : null;
        // добавляем время в финишную дату, чтобы отсчёт шёл до определённого времени - 23:59:59
        if (dateTo != null) {
            Calendar cfrom = Calendar.getInstance();
            cfrom.set(Calendar.HOUR_OF_DAY, 23); // устанавливаем час в 23
            cfrom.set(Calendar.MINUTE, 59); // устанавливаем минуты в 59
            cfrom.set(Calendar.SECOND, 59); // устанавливаем секунды в 59
            cfrom.set(Calendar.MILLISECOND, 999); // устанавливаем миллисекунды в 999
            dateTo = cfrom.getTime(); // устанавливаем нолевое время в запрашиваемую дату
        }

        String sortDirection = searcher.getSortDirection();
        // Назначаем направление сортировки через специальный объект Sort.Direction
        Sort.Direction direction = sortDirection == null || sortDirection.trim().equals("") || sortDirection.trim().equals("asc")? Sort.Direction.ASC : Sort.Direction.DESC;

        String column = searcher.getColumn() != null? searcher.getColumn() : null;
        // создаём объект типа sort, который будет отвечать за сортировку; в конструктор помещаем направление в виде объекта Sort.Direction (которое мы выяснили ранее), название колонки, по которой сортируем и название колонки для сортировки второго плана
        Sort sort = Sort.by(direction, column, "id");

        Integer pageNumber = searcher.getPageNumber() != null? searcher.getPageNumber() : null;
        Integer pageSize = searcher.getPageSize() != null? searcher.getPageSize() : null;

        // создаём объект типа PageRequest, который принимает в конструктор номер страницы, размер страницы и тип сортировки
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Tasks> request = service.findByParams(name, done, categoryId, priorityId, dateFrom, dateTo, pageRequest);
        return ResponseEntity.ok(request);

    }
}
