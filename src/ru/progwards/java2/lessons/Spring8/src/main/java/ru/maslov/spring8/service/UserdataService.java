package ru.maslov.spring8.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.maslov.spring8.entity.Userdata;
import ru.maslov.spring8.repository.UserdataRepository;

@Service
@Transactional
@AllArgsConstructor
public class UserdataService {
    private UserdataRepository repo;

    public void add(Userdata ud) {
        repo.save(ud);
    }

    public Userdata get(Integer id) {
        return repo.findById(id).get();
    }

    public void update(Userdata ud) {
        repo.save(ud);
    }
}
