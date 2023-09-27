package com.maslov.springbootjb.service;

import com.maslov.springbootjb.entity.UserStats;
import com.maslov.springbootjb.repo.UserStatsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserStatsService {
    private final UserStatsRepository repository;

    public UserStats findById(long id) {
        return repository.findById(id).get();
    }

    // Получение всех элементов типа UserStats по значению поля finished
    public List<UserStats> findByFinished(long finished) {
        return repository.findUserStatsByFinishedIs(finished);
    }

    // Получение элемента типа UserStats по полю email объекта UserData, у которых совпадают UserData.id и UserStats.usersid
    public UserStats findUserStatsByUserEmail(String email) {
        return repository.findUserStatsByUserEmail(email);
    }

}
