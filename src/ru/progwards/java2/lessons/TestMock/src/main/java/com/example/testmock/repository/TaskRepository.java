package com.example.testmock.repository;

import com.example.testmock.entity.PriorityType;
import com.example.testmock.entity.Task;
import com.example.testmock.entity.Type;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

@Repository
public class TaskRepository {
    private static ArrayList<Task> tasks = new ArrayList<>();

    static {
        tasks.add(new Task(1L, "First", new Date(), PriorityType.MINOR, Type.TASK));
        tasks.add(new Task(2L, "Second", new Date(), PriorityType.MEDIUM, Type.BUG));
        tasks.add(new Task(3L, "Third", new Date(), PriorityType.MAJOR, Type.TASK));
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(Long id) {
        return tasks.stream().filter(e -> e.getId() == id).findFirst().get();
    }

    public boolean update(Task task) {
        Task result = this.delete(task.getId());
        if (result != null) {
            this.add(task);
        }
        return result != null;
    }

    public Task delete(Long id) {
        Task result = null;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                result = tasks.remove(i);
                break;
            }
        }
        return result;
    }

    public ArrayList<Task> get() {
        return tasks;
    }

    public String getInfo(String type) {
        if (type.equalsIgnoreCase("GET")) {
            return "in get method";
        } else {
            return "Unknown type";
        }
    }

    public Task getById(int id) {
        Task result = null;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                result = tasks.get(i);
                break;
            }
        }
        return result;
    }
}