package com.example.testmock.service;

import com.example.testmock.entity.Task;
import com.example.testmock.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public ArrayList<Task> get() {
        return this.repository.get();
    }

    public void add(Task task) {
        this.repository.add(task);
    }

    public Task get(Long id) {
        return this.repository.get(id);
    }

    public boolean update(Task task) {
        return this.repository.update(task);
    }

    public Task delete(Long id) {
        return this.repository.delete(id);
    }

    public String testget() {
        return this.repository.getInfo("GET");
    }

    public Task getById(int id) {
        return this.repository.getById(id);
    }
}
