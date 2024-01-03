package com.example.testmock.service;

import com.example.testmock.entity.Task;
import com.example.testmock.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

public class TaskMockRepository extends TaskRepository {
    @Override
    public void add(Task task) {
        System.out.println("Test add method worked!");
    }

    @Override
    public Task get(Long id) {
        return new Task();
    }

    @Override
    public boolean update(Task task) {
        return true;
    }

    @Override
    public Task delete(Long id) {
        Task task = new Task();
        task.setId(id);
        return task;
    }

    @Override
    public ArrayList<Task> get() {
        return new ArrayList<>(List.of(new Task(), new Task(), new Task()));
    }


    @Override
    public Task getById(int id) {
        Task task = new Task();
        task.setId(id);
        return task;
    }
}
