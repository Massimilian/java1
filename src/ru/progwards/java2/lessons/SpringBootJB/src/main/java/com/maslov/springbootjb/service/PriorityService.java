package com.maslov.springbootjb.service;

import com.maslov.springbootjb.entity.Priority;
import com.maslov.springbootjb.repo.PriorityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PriorityService {
    private final PriorityRepository repository;

    public PriorityService(PriorityRepository repository) {
        this.repository = repository;
    }

    public Priority findById(long id) {
        return repository.findById(id).get();
    }

    public Priority add(Priority priority) {
        return repository.save(priority);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
