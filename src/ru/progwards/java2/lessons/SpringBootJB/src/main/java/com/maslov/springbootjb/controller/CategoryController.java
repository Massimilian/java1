package com.maslov.springbootjb.controller;

import com.maslov.springbootjb.entity.Category;
import com.maslov.springbootjb.search.CategorySearchValues;
import com.maslov.springbootjb.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController // помечаем этот класс, как контроллер типа REST
@RequestMapping("/category") // устанавливаем изначальный (корневой) адрес (URL)
public class CategoryController {
    private CategoryService service; // устанавливаем связь со слоем service

    // внедряем нужный класс; внедрение через аннотацию @Autowired "is not recommended"
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    // указываем уточнение к адресу - теперь при вводе номера категории это число будет помещаться в метод ниже
    public Category findById(@PathVariable("id") Integer id) { // вытаскивание значения из mapping и вкладывание его в качестве аргумента
        return service.findById(id); // вызов метода сервиса
    }

    // метод поиска Category по параметру name - get-версия
    @GetMapping("/name/{name}")
    public Category findByName(@PathVariable("name") String name) {
        return service.findByName(name);
    }

    // метод поиска Category по параметру name - post-версия
    @PostMapping("/name")
    public Category findByNamePostVersion(@RequestBody String name){
        return service.findByName(name);
    }


    @GetMapping("findByProcessed/{number}")
    public List<Category> findByProcessed(@PathVariable("number") int number) {
        return service.findCategoryByInProcessIs(number);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Category>> findByProcessedPostVersion(@RequestBody CategorySearchValues csv) {
        // проверка на некорректные или отсутствующие параметры
        if (csv.getInProcess() <= 0) {
            return new ResponseEntity("Impossible value.", HttpStatus.NOT_ACCEPTABLE);
        }
        List<Category> list = service.findCategoryByInProcessIs(csv.getInProcess());
        return ResponseEntity.ok(list);
    }

    @PostMapping("/findById")
    public ResponseEntity<Category> findById(@RequestBody int id) {
        Category cat = null;
        try { // проверка на случай, если подобная category не будет найдена
            cat = service.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("There is no category with such id", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(cat);
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category) {
        // id создаётся автоматически; поэтому, если в нашей Category уже прописан id - то это является ошибкой
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
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Category category) {
        // возвращаем ошибку в случае, когда категория нолевая (невозможная ситуация)
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("missed param (id must be null)", HttpStatus.NOT_ACCEPTABLE);
        }
        // возвращаем "статус - не-ОК", когда не заполнен параметр catname
        if (category.getCatname() == null || category.getCatname().trim().length() == 0) {
            return new ResponseEntity("missed param - title must be not null", HttpStatus.NOT_ACCEPTABLE);
        }
        // если всё хорошо - обновляем category
        service.update(category);
        // возвращаем соответствующий статус - 200 ОК
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
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

}
