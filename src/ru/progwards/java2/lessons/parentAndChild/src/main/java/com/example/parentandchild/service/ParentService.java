package com.example.parentandchild.service;

import com.example.parentandchild.entity.Parent;
import com.example.parentandchild.repo.ParentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ParentService {
    ParentRepository repo;

    public ParentService(ParentRepository repository) {
        this.repo = repository;
    }

    public Parent findById(Integer id) {
        return repo.findById(id).get();
    }

    public Parent add(Parent p) {
        return repo.save(p);
    }

    public void update(Parent p) {
        repo.save(p);
    }

    public void deleteById(int id) {
        repo.deleteById(id);
    }
}
