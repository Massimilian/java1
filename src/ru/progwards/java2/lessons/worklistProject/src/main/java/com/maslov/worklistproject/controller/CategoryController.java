package com.maslov.worklistproject.controller;

import com.maslov.worklistproject.entity.Category;
import com.maslov.worklistproject.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService service;

    @GetMapping("/{id}") // пример запроса: localhost:8081/category/1
    public Category findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping("/byname") // пример запроса: localhost:8081/category/byname {тело запроса - 'home'}
    public Category findByName(@RequestBody String name) {
        return service.findByName(name);
    }

    @PostMapping("/byinprocess") // пример запроса: localhost:8081/category/byinprocess {тело запроса - '[1, 2]'}
    public Category[] findByInProcessNums(@RequestBody String[] fromTo) {
        return service.findByInProcessNums(fromTo);
    }

    @GetMapping("/finishedisnull") // пример запроса: localhost:8081/category/finishedisnull
    public Category[] findByFinishedisNull() {
        return service.findByFinishedCount(0);
    }

    @GetMapping("/finishedcount/{count}") // пример запроса: localhost:8081/category/finishedcount/1
    public Category[] findByFinishedCount(@PathVariable("count") int count) {
        return service.findByFinishedCount(count);
    }

    @PostMapping("/add") // пример запроса: localhost:8081/category/add  | тело запроса:
                            //{
                            //    "catname": "WALK",
                            //        "inProcess": 0,
                            //        "finished": 0
                            //}
    public ResponseEntity<Category> add(@RequestBody Category category) {
        // id создаётся автоматически самой БД (это прописано в её настройках); поэтому, если в нашей Category уже прописан id - то это является ошибкой
        if (category.getId() != null && category.getId() != 0) {
            // возвращаем ResponseEntity с указанием на ошибку
            return new ResponseEntity("redundant param (id must be null)", HttpStatus.NOT_ACCEPTABLE);
        }
        // параметр catName не может быть пустым, если он пустой - возвращаем ошибку
        if (category.getCatname() == null || category.getCatname().equals("")) {
            // возвращаем ResponseEntity с указанием на ошибку
            return new ResponseEntity("missed param - title must be not null", HttpStatus.NOT_ACCEPTABLE);
        }
        // если всё в порядке - добавляем объект Category при помощи метода add
        return ResponseEntity.ok(service.add(category));
    }

    // на этот раз используется аннотация @PutMapping, так как используемый метод HTTP - PUT
    @PutMapping("/update") // пример запроса: localhost:8081/category/update  | тело запроса:
                            //{
                            //        "id": 24,
                            //        "catname": "WALKING",
                            //        "inProcess": 0,
                            //        "finished": 0
                            //}
    public ResponseEntity update(@RequestBody Category category) {
        // возвращаем ошибку в случае, когда категория нолевая (невозможная ситуация)
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("missed param (id must be null)", HttpStatus.NOT_ACCEPTABLE);
        }
        // возвращаем "статус - не-ОК", когда не заполнен параметр catname
        if (category.getCatname() == null || category.getCatname().trim().length() == 0) {
            return new ResponseEntity("missed param - title must be not null", HttpStatus.NOT_ACCEPTABLE);
        }
        // если всё хорошо - обновляем cayrgory
        service.update(category);
        // возвращаем соответствующий статус - 200 ОК (для сравнения - в методе add мы возвращали сам объект)
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}") // пример запроса: localhost:8081/category/delete/22
    public ResponseEntity delete(@PathVariable("id") long id) {
        try {
            // проверяем – существует ли по данному id элемент (если нет – выбросится ошибка)
            service.findById(id);
            // вызываем соответствующий метод из repository (будет выполнена, если не случится ошибка)
            service.deleteById(id);
        } catch (NoSuchElementException e) {
            // отработает в случае, когда по данному id не будет найдено ничего
            e.printStackTrace();
            return new ResponseEntity("id '" + id + "' has not found.", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/search") // пример запроса: localhost:8081/category/search {тело запроса - '1'}
    public List<Category> search(@RequestBody Long id) {
        return service.findCategoryByIdIs(id);
    }

    @PostMapping("/getminmax") // пример запроса: localhost:8081/category/getminmax {тело запроса - '[1, 7]'}
    public List<Category> getByIdFromMinToMax(@RequestBody Long[] minmax) {
        return service.getByIdFromMinToMax(minmax);
    }
}
