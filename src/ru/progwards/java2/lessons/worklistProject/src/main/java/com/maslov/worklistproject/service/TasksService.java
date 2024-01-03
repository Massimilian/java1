package com.maslov.worklistproject.service;

import com.maslov.worklistproject.entity.Tasks;
import com.maslov.worklistproject.repo.TasksRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class TasksService {
    TasksRepository repo;

    public Tasks get(Integer id) {
        return repo.findById(id).get();
    }

    public Tasks getByName(String name) {
        return repo.findTasksByNameIs(name);
    }

    public Tasks add(Tasks task) {
        return repo.save(task);
    }
}
