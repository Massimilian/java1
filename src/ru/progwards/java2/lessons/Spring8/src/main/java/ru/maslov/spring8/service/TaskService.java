package ru.maslov.spring8.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.maslov.spring8.entity.Task;
import ru.maslov.spring8.entity.Userdata;
import ru.maslov.spring8.repository.TaskRepository;

@Service
@Transactional
@AllArgsConstructor
public class TaskService {
    private TaskRepository repo;

    public Page<Task> getAllTasks(PageRequest pr) {
        return repo.getAllTasks(pr);
    }

    public Task deleteById(Integer id) {
        Task request = repo.findById(id).get();
        repo.deleteById(id);
        return request;
    }

    public void create(Task task) {
        repo.save(task);
    }

    public Task findById(Integer id) {
        return repo.findById(id).get();
    }

    public Userdata getAuthor(String login) {
        return repo.getAuthor(login);
    }

}
