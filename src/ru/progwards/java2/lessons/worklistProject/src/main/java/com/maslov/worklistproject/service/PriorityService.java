package com.maslov.worklistproject.service;

import com.maslov.worklistproject.entity.Priority;
import com.maslov.worklistproject.repo.PriorityRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
@Transactional
public class PriorityService {
    private PriorityRepository repo;

    public List<Priority> getAll() {
        return repo.findAll();
    }

    public Priority getById(Long id) {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException nsee) {
            return null;
        }
    }

    public Priority edit(Priority priority) {
        return repo.save(priority);
    }

    public Priority add(Priority priority) {
        return repo.save(priority);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
