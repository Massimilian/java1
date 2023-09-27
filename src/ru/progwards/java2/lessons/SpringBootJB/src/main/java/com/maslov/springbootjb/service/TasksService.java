package com.maslov.springbootjb.service;

import com.maslov.springbootjb.entity.Tasks;
import com.maslov.springbootjb.repo.TasksRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TasksService {
    private final TasksRepository repository;

    public TasksService(TasksRepository repository) {
        this.repository = repository;
    }

    // Метод по поиску Tasks по полю userDataId, упорядочивающий по полю name
    public List<Tasks> findByUserId(Integer id) {
        return repository.findByUserDataIdOrderByNameAsc(id);
    }

    // Метод по поиску Tasks по полю dateandtime, принимающее два значения и ищущее все значения в промежутке
    public List<Tasks> findByDateandtimeBetween(Timestamp from, Timestamp to) {
        return repository.findByDateandtimeBetween(from, to);
    }

    // Метод по поиску Tasks по имени (name)
    public List<Tasks> findByName(String name) {
        return repository.findByNameIgnoreCase(name);
    }

    public Tasks add(Tasks tasks) {
        return repository.save(tasks);
    }

    public void update(Tasks tasks) {
        repository.save(tasks);
    }

    // Метод по поиску Tasks по полю mail пользователя
    public List<Tasks> findByUserEmail(String email) {
        return repository.findByUserEmail(email);
    }

    // метод постраничного вывода Tasks, в которых совпадут либо окажутся нолевыми некоторые параметры (если нолевыми окажутся все параметры - буду выведены все Tasks)
    public Page<Tasks> findByParams(String name, Boolean done, Integer categoryId, Integer priorityId, Date dateFrom, Date dateTo, Pageable pageable) {
        return repository.findByParams(name, done, categoryId, priorityId, dateFrom, dateTo, pageable);
    }
}
