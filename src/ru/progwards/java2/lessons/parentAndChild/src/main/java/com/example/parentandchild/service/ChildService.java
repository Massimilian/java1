package com.example.parentandchild.service;

import com.example.parentandchild.entity.Child;
import com.example.parentandchild.repo.ChildRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ChildService {
    ChildRepository repo;

    public ChildService(ChildRepository repository) {
        this.repo = repository;
    }

    public Child findById(Integer id) {
        return repo.findById(id).get();
    }

    public Child add(Child ch) {
        return repo.save(ch);
    }

    public void update(Child ch) {
        repo.save(ch);
    }

    public void deleteById(int id) {
        repo.deleteById(id);
    }
}
